package com.redescooter.ses.web.ros.service.website.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.redescooter.ses.api.common.constant.EamilConstant;
import com.redescooter.ses.api.common.enums.customer.CustomerSourceEnum;
import com.redescooter.ses.api.common.enums.customer.CustomerStatusEnum;
import com.redescooter.ses.api.common.enums.inquiry.InquiryPayStatusEnums;
import com.redescooter.ses.api.common.enums.inquiry.InquirySourceEnums;
import com.redescooter.ses.api.common.enums.inquiry.InquiryStatusEnums;
import com.redescooter.ses.api.common.enums.website.AccessoryTypeEnums;
import com.redescooter.ses.api.common.enums.website.ProductModelEnums;
import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.starter.common.config.SendinBlueConfig;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.DateUtil;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.tool.utils.accountType.RsaUtils;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.website.WebsiteInquiryServiceMapper;
import com.redescooter.ses.web.ros.dm.OpeCustomer;
import com.redescooter.ses.web.ros.dm.OpeCustomerAccessories;
import com.redescooter.ses.web.ros.dm.OpeCustomerInquiry;
import com.redescooter.ses.web.ros.dm.OpeCustomerInquiryB;
import com.redescooter.ses.web.ros.dm.OpePartsProduct;
import com.redescooter.ses.web.ros.dm.OpeSysUser;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.*;
import com.redescooter.ses.web.ros.service.customer.CustomerRosService;
import com.redescooter.ses.web.ros.service.website.WebsiteOrderFormService;
import com.redescooter.ses.web.ros.vo.website.*;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisCluster;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName:WebsiteInquiryServiceImpl
 * @description: WebsiteInquiryServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2020/05/12 15:56
 */
@Slf4j
@Service
public class WebsiteInquiryServiceImpl implements WebsiteOrderFormService {

    @Autowired
    private OpeCustomerInquiryService opeCustomerInquiryService;

    @Autowired
    private WebsiteInquiryServiceMapper websiteInquiryServiceMapper;

    @Autowired
    private OpeCustomerAccessoriesService opeCustomerAccessoriesService;

    @Autowired
    private OpePartsProductService opePartsProductService;

    @Autowired
    private OpeCustomerInquiryBService opeCustomerInquiryBService;

    @Autowired
    private CustomerRosService customerRosService;

    @Autowired
    private OpeCustomerService opeCustomerService;

    @Reference
    private IdAppService idAppService;

    @Autowired
    private JedisCluster jedisCluster;

    @Autowired
    private OpeSysUserService opeSysUserService;

    @Value("${Request.privateKey}")
    private String privatekey;

    @Autowired
    private SendinBlueConfig sendinBlueConfig;

    /**
     * 车辆型号
     *
     * @param enter
     * @return
     */
    @Override
    public List<ProductModelResult> productModels(GeneralEnter enter) {
        List<ProductModelResult> resultList = new ArrayList<>();

        for (ProductModelEnums item : ProductModelEnums.values()) {
            resultList.add(ProductModelResult.builder().modelCode(item.getValue()).name(item.getCode()).build());
        }
        return resultList;
    }

    /**
     * 车辆列表
     *
     * @param enter
     * @return
     */
    @Override
    public List<ProductResult> scooters(ScootersEnter enter) {
        if (ProductModelEnums.getProductModelEnumsByValue(enter.getModelCode()) == null) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }
        return websiteInquiryServiceMapper.scooters(enter);
    }


    /**
     * 配件电池类型
     *
     * @param enter
     * @return
     */
    @Override
    public List<AccessoryResult> accessoryBatteryList(GeneralEnter enter) {
        return websiteInquiryServiceMapper.accessoryList(AccessoryTypeEnums.BATTERY.getValue());
    }


    /**
     * 后备箱
     *
     * @param enter
     * @return
     */
    @Override
    public List<AccessoryResult> accessoryTopCaseList(GeneralEnter enter) {
        return websiteInquiryServiceMapper.accessoryList(AccessoryTypeEnums.TOP_CASE.getValue());
    }

    /**
     * 保存 询价单
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public SaveOrderFormResult saveOrderForm(SaveSaleOrderEnter enter) {
        //入参对象去空格
        SesStringUtils.objStringTrim(enter);

        //电话解密
        String decrypt = null;
        try {
            decrypt = RsaUtils.decrypt(enter.getPhone(), privatekey);
        } catch (Exception e) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }
        if (decrypt.length() != 10) {
            throw new SesWebRosException(ExceptionCodeEnums.TELEPHONE_IS_NOT_ILLEGAL.getCode(), ExceptionCodeEnums.TELEPHONE_IS_NOT_ILLEGAL.getMessage());
        }
        enter.setPhone(decrypt);


        //判断当前客户已经为正式客户 如果为正式客户 不允许添加 预订单
        OpeCustomer opeCustomer = opeCustomerService.getById(enter.getUserId());
        if (opeCustomer == null) {
            throw new SesWebRosException(ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getCode(), ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getMessage());
        }
        if (StringUtils.equals(opeCustomer.getStatus(), CustomerStatusEnum.OFFICIAL_CUSTOMER.getValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.CUSTOMER_NOT_ALLOWED_TO_CREATED_INQUIRY.getCode(), ExceptionCodeEnums.CUSTOMER_NOT_ALLOWED_TO_CREATED_INQUIRY.getMessage());
        }
        //订单校验客户订单校验 一个客户只允许存在一个订单
//        List<OpeCustomerInquiry> customerInquiryList = opeCustomerInquiryService.list(new LambdaQueryWrapper<OpeCustomerInquiry>().eq(OpeCustomerInquiry::getEmail, opeCustomer.getEmail()));
//        if (CollectionUtils.isNotEmpty(customerInquiryList)) {
//            throw new SesWebRosException(ExceptionCodeEnums.CUSTOMER_ALREADY_EXIST_ORDER_FORM.getCode(), ExceptionCodeEnums.CUSTOMER_ALREADY_EXIST_ORDER_FORM.getMessage());
//        }
        //后备箱 校验
        OpeCustomerAccessories topCase = null;
        if (enter.getBuyTopCase()) {
            topCase = opeCustomerAccessoriesService.getById(enter.getTopCaseId());
            if (topCase == null) {
                throw new SesWebRosException(ExceptionCodeEnums.TOP_CASE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.TOP_CASE_IS_NOT_EXIST.getMessage());
            }
        }

        //电池的校验
        OpeCustomerAccessories battery = opeCustomerAccessoriesService.getById(enter.getAccessoryBatteryId());
        if (battery == null) {
            throw new SesWebRosException(ExceptionCodeEnums.BATTERY_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.BATTERY_IS_NOT_EXIST.getMessage());
        }

        //产品校验
        ProductResult product = websiteInquiryServiceMapper.queryProductById(enter.getProductId());
        if (product == null) {
            throw new SesWebRosException(ExceptionCodeEnums.PART_PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PART_PRODUCT_IS_NOT_EXIST.getMessage());
        }

        //电池要求过滤
        BigDecimal totalPrice = checkBatteryQty(enter, product, battery.getPrice());

        //配件保存集合
        List<OpeCustomerInquiryB> opeCustomerInquiryBList = new ArrayList<>();
        //总价格计算
        if (enter.getBuyTopCase()) {
            totalPrice = totalPrice.add(topCase.getPrice());
        }

        //todo 测试暂定为0.5 之后要改掉 默认100美分
//        totalPrice = new BigDecimal("100");
//        totalPrice = new BigDecimal("19000");

        //生成主订单
        OpeCustomerInquiry opeCustomerInquiry = buildOpeCustomerInquiry(enter, product, totalPrice, idAppService.getId(SequenceName.OPE_CUSTOMER_INQUIRY));
        opeCustomerInquiry.setCreatedBy(enter.getUserId());
        opeCustomerInquiry.setCreatedTime(new Date());

        //生成子订单
        if (enter.getBuyTopCase()) {
            //后备箱形成子表记录
            opeCustomerInquiryBList.add(buildAccessory(enter, opeCustomerInquiry.getId(), topCase.getPrice(), AccessoryTypeEnums.TOP_CASE.getValue()));
        }
        //电池记录
        opeCustomerInquiryBList.add(buildAccessory(enter, opeCustomerInquiry.getId(), battery.getPrice(), AccessoryTypeEnums.BATTERY.getValue()));
        //子表数据 保存
        if (CollectionUtils.isNotEmpty(opeCustomerInquiryBList)) {
            opeCustomerInquiryBService.saveBatch(opeCustomerInquiryBList);
        }
        //主订单保存
        opeCustomerInquiryService.save(opeCustomerInquiry);
        return SaveOrderFormResult.builder().id(opeCustomerInquiry.getId()).build();
    }

    /**
     * 修改 预订单
     *
     * @param enter
     * @return
     */
    @Override
    public SaveOrderFormResult editOrderForm(SaveSaleOrderEnter enter) {

        //入参对象去空格
        SesStringUtils.objStringTrim(enter);

        OpeCustomerInquiry customerInquiry = opeCustomerInquiryService.getById(enter.getId());
        if (customerInquiry == null) {
            throw new SesWebRosException(ExceptionCodeEnums.INQUIRY_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.INQUIRY_IS_NOT_EXIST.getMessage());
        }

        //订单校验客户订单校验 一个客户只允许存在一个订单
//        List<OpeCustomerInquiry> customerInquiryList = opeCustomerInquiryService.list(new LambdaQueryWrapper<OpeCustomerInquiry>()
//                .eq(OpeCustomerInquiry::getEmail, customerInquiry.getEmail())
//        .gt(OpeCustomerInquiry::getId,customerInquiry.getId())
//        );
//        if (CollectionUtils.isNotEmpty(customerInquiryList)) {
//            throw new SesWebRosException(ExceptionCodeEnums.CUSTOMER_ALREADY_EXIST_ORDER_FORM.getCode(), ExceptionCodeEnums.CUSTOMER_ALREADY_EXIST_ORDER_FORM.getMessage());
//        }


        //后备箱 校验
        OpeCustomerAccessories topCase = null;
        if (enter.getBuyTopCase()) {
            topCase = opeCustomerAccessoriesService.getById(enter.getTopCaseId());
            if (topCase == null) {
                throw new SesWebRosException(ExceptionCodeEnums.TOP_CASE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.TOP_CASE_IS_NOT_EXIST.getMessage());
            }
        }

        //电池的校验
        OpeCustomerAccessories battery = opeCustomerAccessoriesService.getById(enter.getAccessoryBatteryId());
        if (battery == null) {
            throw new SesWebRosException(ExceptionCodeEnums.BATTERY_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.BATTERY_IS_NOT_EXIST.getMessage());
        }

        //产品校验
        ProductResult product = websiteInquiryServiceMapper.queryProductById(enter.getProductId());
        if (product == null) {
            throw new SesWebRosException(ExceptionCodeEnums.PART_PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PART_PRODUCT_IS_NOT_EXIST.getMessage());
        }
        if (enter.getPhone() != null) {
            if (enter.getPhone() != null) {
                String decrypt = null;
                try {

                    decrypt = RsaUtils.decrypt(enter.getPhone(), privatekey);
                } catch (Exception e) {
                    throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
                }
                enter.setPhone(decrypt);
            }
        }

        //电池要求过滤
        BigDecimal totalPrice = checkBatteryQty(enter, product, battery.getPrice());

        //配件保存集合
        List<OpeCustomerInquiryB> opeCustomerInquiryBList = new ArrayList<>();
        //总价格计算

        if (enter.getBuyTopCase()) {
            totalPrice = totalPrice.add(topCase.getPrice());
        }
//        totalPrice = new BigDecimal("19000");


        //todo 测试暂定为0.5 之后要改掉 100美分
//        totalPrice = new BigDecimal("100");
        //生成主订单
        OpeCustomerInquiry opeCustomerInquiry = buildOpeCustomerInquiry(enter, product, totalPrice, enter.getId());
        opeCustomerInquiry.setCreatedBy(enter.getUserId());
        opeCustomerInquiry.setCreatedTime(new Date());

        //生成子订单
        if (enter.getBuyTopCase()) {
            //后备箱形成子表记录
            opeCustomerInquiryBList.add(buildAccessory(enter, opeCustomerInquiry.getId(), topCase.getPrice(), AccessoryTypeEnums.TOP_CASE.getValue()));
        }
        //电池记录
        opeCustomerInquiryBList.add(buildAccessory(enter, opeCustomerInquiry.getId(), battery.getPrice(), AccessoryTypeEnums.BATTERY.getValue()));

        //子订单保存
        List<OpeCustomerInquiryB> inquiryBList = opeCustomerInquiryBService.list(new LambdaQueryWrapper<OpeCustomerInquiryB>().eq(OpeCustomerInquiryB::getInquiryId, enter.getId()));
        if (CollectionUtils.isNotEmpty(inquiryBList)) {
            opeCustomerInquiryBService.removeByIds(inquiryBList.stream().map(OpeCustomerInquiryB::getId).collect(Collectors.toList()));
        }
        opeCustomerInquiryBService.saveBatch(opeCustomerInquiryBList);

        //主订单保存
        opeCustomerInquiryService.save(opeCustomerInquiry);
        return SaveOrderFormResult.builder().id(opeCustomerInquiry.getId()).build();
    }

    /**
     * 询价单封装
     *
     * @param enter
     * @param product
     * @param totalPrice
     * @return
     */
    private OpeCustomerInquiry buildOpeCustomerInquiry(SaveSaleOrderEnter enter, ProductResult product, BigDecimal totalPrice, Long id) {

        //查询客户个人信息
        OpeCustomer opeCustomer = opeCustomerService.getById(enter.getUserId());
        if (opeCustomer == null) {
            throw new SesWebRosException(ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getCode(), ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getMessage());
        }

        OpeCustomerInquiry opeCustomerInquiry = new OpeCustomerInquiry();
        opeCustomerInquiry.setId(id);
        opeCustomerInquiry.setDr(0);
        opeCustomerInquiry.setOrderNo(RandomStringUtils.randomAlphabetic(8));
        opeCustomerInquiry.setCustomerId(enter.getUserId());
        opeCustomerInquiry.setFirstName(SesStringUtils.upperCaseString(opeCustomer.getCustomerFirstName()));
        opeCustomerInquiry.setLastName(SesStringUtils.upperCaseString(opeCustomer.getCustomerLastName()));
        opeCustomerInquiry.setFullName(SesStringUtils.upperCaseString(opeCustomer.getCustomerFirstName()) + SesStringUtils.upperCaseString(opeCustomer.getCustomerLastName()));
        opeCustomerInquiry.setEmail(opeCustomer.getEmail());
        opeCustomerInquiry.setCustomerSource(CustomerSourceEnum.WEBSITE.getValue());
        opeCustomerInquiry.setStatus(InquiryStatusEnums.UNPAY_DEPOSIT.getValue());
        opeCustomerInquiry.setProductId(enter.getProductId());
        opeCustomerInquiry.setProductModel(enter.getProductModel());
        opeCustomerInquiry.setProductPrice(product.getPrice());
        opeCustomerInquiry.setTotalPrice(totalPrice);
        //todo 目前暂做个人端 默认车辆数量为一
//        opeCustomerInquiry.setScooterQuantity(enter.getProductQty());
        opeCustomerInquiry.setScooterQuantity(1);
        opeCustomerInquiry.setPayStatus(InquiryPayStatusEnums.UNPAY_DEPOSIT.getValue());
        opeCustomerInquiry.setAddress(enter.getAddress());
        opeCustomerInquiry.setCountryCode(enter.getCountryCode());
        opeCustomerInquiry.setTelephone(enter.getPhone());
        opeCustomerInquiry.setBankCardName(enter.getBankCardName());
        opeCustomerInquiry.setSource(InquirySourceEnums.ORDER_FORM.getValue());
        opeCustomerInquiry.setCardNum(StringUtils.isEmpty(enter.getCardNum()) ? null : enter.getCardNum());
        opeCustomerInquiry.setExpiredTime(enter.getExpiredTime() != null && enter.getExpiredTime() != 0 ? DateUtil.timeStampToDate(enter.getExpiredTime(), DateUtil.UTC) : null);
        opeCustomerInquiry.setCvv(StringUtils.isBlank(enter.getCvv()) ? null : enter.getCvv());
        opeCustomerInquiry.setPostalCode(StringUtils.isBlank(enter.getPostalCode()) ? null : enter.getPostalCode());
        opeCustomerInquiry.setUpdatedBy(enter.getUserId());
        opeCustomerInquiry.setUpdatedTime(new Date());
        return opeCustomerInquiry;
    }

    /**
     * 定金支付
     *
     * @param enter
     * @return 1、定金支付
     * 2、询价单 状态 未处理-----》已处理
     * 3、判断当前是否存在 存在-- 车辆数量累加 不存在 客户状态 预定客户---》 潜在客户
     */
    @Override
    public GeneralResult payDeposit(IdEnter enter) {
        return null;
    }

    /**
     * 预订单列表
     *
     * @param enter
     * @return
     */
    @Override
    public List<OrderFormsResult> orderForms(OrderFormsEnter enter) {
        return websiteInquiryServiceMapper.orderForms(enter);
    }

    /**
     * 订单详情
     *
     * @param enter
     * @return
     */
    @Override
    public OrderFormInfoResult orderFormInfo(IdEnter enter) {
        OpeCustomerInquiry customerInquiry = opeCustomerInquiryService.getById(enter.getId());
        if (customerInquiry == null) {
            throw new SesWebRosException(ExceptionCodeEnums.INQUIRY_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.INQUIRY_IS_NOT_EXIST.getMessage());
        }
        //查询产品
        OpePartsProduct opePartsProduct = opePartsProductService.getById(customerInquiry.getProductId());
        if (opePartsProduct == null) {
            throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
        }

        //查询子订单
        List<OpeCustomerInquiryB> inquiryBList = opeCustomerInquiryBService.list(new LambdaQueryWrapper<OpeCustomerInquiryB>().eq(OpeCustomerInquiryB::getInquiryId, customerInquiry.getId()));
        if (CollectionUtils.isEmpty(inquiryBList)) {
            return null;
        }
    /*  String decryptTelephone =null;
      try {

         decryptTelephone = RsaUtils.encryptByPrivateKey(customerInquiry.getTelephone(), privatekey);
      }catch (Exception e){
        throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
      }*/
        //反参对象
        OrderFormInfoResult result = OrderFormInfoResult.builder()
                .id(customerInquiry.getId())
                .orderNo(customerInquiry.getOrderNo())
                .address(customerInquiry.getAddress())
                .countryCode(customerInquiry.getCountryCode())
                .phone(customerInquiry.getTelephone())
                .productId(customerInquiry.getProductId())
                .produceModel(customerInquiry.getProductModel())
                .productQty(customerInquiry.getScooterQuantity())
                .bankCardName(customerInquiry.getBankCardName())
                .cardNum(customerInquiry.getCardNum())
                .expiredTime(customerInquiry.getExpiredTime())
                .cvv(customerInquiry.getCvv())
                .postalCode(customerInquiry.getPostalCode())
                .totalPrice(customerInquiry.getTotalPrice())
                .remainingPrice(customerInquiry.getTotalPrice().subtract(new BigDecimal("190")))
                .color(opePartsProduct.getColor())
                .status(customerInquiry.getStatus())
                .build();

        //封装配件数量
        inquiryBList.forEach(item -> {
            if (item.getProductType().equals(AccessoryTypeEnums.BATTERY.getValue())) {
                result.setAccessoryBatteryId(item.getProductId());
                result.setAccessoryBatteryQty(item.getProductQty());
            }
            if (item.getProductType().equals(AccessoryTypeEnums.TOP_CASE.getValue())) {
                result.setTopCaseId(item.getProductId());
            }
        });
        return result;
    }

    /**
     * 尾款支付
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult payLastParagraph(GeneralEnter enter) {
        return null;
    }

    /**
     * 邮箱验证
     *
     * @param enter
     * @return
     */
    @Override
    public BooleanResult checkMail(CheckEmailEnter enter) {
        String decrypt = null;
        try {

            decrypt = RsaUtils.decrypt(enter.getEmail(), privatekey);
        } catch (Exception e) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(),
                    ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }
        enter.setEmail(decrypt);

        StringEnter stringEnter = new StringEnter(enter.getEmail());
        //邮箱校验
        IntResult checkMailCount = customerRosService.checkMailCount(stringEnter);
        return BooleanResult.builder().success(checkMailCount.getValue() == 0 ? Boolean.TRUE : Boolean.FALSE).build();
    }

    /**
     * 客户信息
     *
     * @param enter
     * @return
     */
    @Override
    public CustomerInfoResult customerInfo(GeneralEnter enter) {
        OpeSysUser opeSysUser = opeSysUserService.getById(enter.getUserId());
        if (opeSysUser == null) {
            throw new SesWebRosException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
        OpeCustomer opeCustomer = opeCustomerService.getOne(new LambdaQueryWrapper<OpeCustomer>().eq(OpeCustomer::getEmail,opeSysUser.getLoginName()).eq(OpeCustomer::getCustomerSource,
                CustomerSourceEnum.WEBSITE.getValue()));
        if (opeCustomer == null) {
            throw new SesWebRosException(ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getCode(), ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getMessage());
        }
     /* String decrypt =null;
      try {

        decrypt = RsaUtils.encryptByPrivateKey(opeCustomer.getEmail(),privatekey);
      }catch (Exception e){
        throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
      }*/
        return CustomerInfoResult.builder()
                .id(opeCustomer.getId())
                .email(opeCustomer.getEmail())
                .firstName(opeCustomer.getCustomerFirstName())
                .lastName(opeCustomer.getCustomerLastName())
                .build();
    }

    /**
     * 存储邮箱
     *
     * @param enter
     */
    @Override
    public GeneralResult email(StorageEamilEnter enter) {
        if (enter.getEmail().isEmpty()) {
            throw new SesWebRosException(ExceptionCodeEnums.MAIL_NAME_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.MAIL_NAME_CANNOT_EMPTY.getMessage());
        }
        String eamil = null;
        try {

            eamil = RsaUtils.decrypt(enter.getEmail(), privatekey);
        } catch (Exception e) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }
        adPush(eamil);
        jedisCluster.set(EamilConstant.SUBSCRIBE_EMAIL + enter.getRequestId(), eamil);
        return new GeneralResult(enter.getRequestId());
    }

    private OpeCustomerInquiryB buildAccessory(SaveSaleOrderEnter enter, Long id, BigDecimal price, String type) {
        OpeCustomerInquiryB opeCustomerInquiryB = new OpeCustomerInquiryB();
        opeCustomerInquiryB.setId(idAppService.getId(SequenceName.OPE_CUSTOMER_INQUIRY_B));
        opeCustomerInquiryB.setDr(0);
        opeCustomerInquiryB.setInquiryId(id);
        opeCustomerInquiryB.setProductId(StringUtils.equals(type, AccessoryTypeEnums.TOP_CASE.getValue()) == true ? enter.getTopCaseId() : enter.getAccessoryBatteryId());
        opeCustomerInquiryB.setProductPrice(price);
        opeCustomerInquiryB.setProductQty(StringUtils.equals(type, AccessoryTypeEnums.TOP_CASE.getValue()) == true ? 1 : enter.getAccessoryBatteryQty());
        opeCustomerInquiryB.setProductType(type);
        opeCustomerInquiryB.setProductId(enter.getProductId());
        opeCustomerInquiryB.setCreatedBy(enter.getUserId());
        opeCustomerInquiryB.setCreatedTime(new Date());
        opeCustomerInquiryB.setUpdatedBy(enter.getUserId());
        opeCustomerInquiryB.setUpdatedTime(new Date());
        return opeCustomerInquiryB;
    }

    /**
     * 校验电池数量
     *
     * @param enter
     * @param product
     */
    private BigDecimal checkBatteryQty(SaveSaleOrderEnter enter, ProductResult product, BigDecimal batteryPrice) {
        int qty = 0;

        switch (product.getProductModel()) {
            case "1":
                //50CC 默认配置 一组电池
                if (enter.getAccessoryBatteryQty() < 1) {
                    throw new SesWebRosException(ExceptionCodeEnums.BATTERIES_DOES_NOT_MEET_THE_STANDARD.getCode(), ExceptionCodeEnums.BATTERIES_DOES_NOT_MEET_THE_STANDARD.getMessage());
                }

                qty = enter.getAccessoryBatteryQty() - 1;
                break;
            case "2":
                //100CC 默认配置 两组电池
                if (enter.getAccessoryBatteryQty() < 2) {
                    throw new SesWebRosException(ExceptionCodeEnums.BATTERIES_DOES_NOT_MEET_THE_STANDARD.getCode(), ExceptionCodeEnums.BATTERIES_DOES_NOT_MEET_THE_STANDARD.getMessage());
                }
                qty = enter.getAccessoryBatteryQty() - 2;

                break;
            case "3":
                //125CC 默认配置 配置四组电池
                if (enter.getAccessoryBatteryQty() < 4) {
                    throw new SesWebRosException(ExceptionCodeEnums.BATTERIES_DOES_NOT_MEET_THE_STANDARD.getCode(), ExceptionCodeEnums.BATTERIES_DOES_NOT_MEET_THE_STANDARD.getMessage());
                }
                break;
        }

        //todo 目前是优惠价 减500欧元
        return product.getPrice().add(batteryPrice.multiply(new BigDecimal(qty))).subtract(new BigDecimal(500));
    }

    private void adPush(String email) {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse(sendinBlueConfig.getMediaType());

        Map<String, String> map = new HashMap<>();
        map.put("updateEnabled", sendinBlueConfig.getUpdateEnabled());
        map.put("email", email);

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(map));
        Request request = new Request.Builder()
                .url(sendinBlueConfig.getUrl())
                .post(body)
                .addHeader("accept", sendinBlueConfig.getAccept())
                .addHeader("content-type", sendinBlueConfig.getContentType())
                .addHeader("api-key", sendinBlueConfig.getApiKey())
                .build();
        try {
            Response response = client.newCall(request).execute();
            System.out.println("response" + response.message());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
