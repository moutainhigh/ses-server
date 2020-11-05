package com.redescooter.ses.web.ros.service.website.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.redescooter.ses.api.common.enums.base.AppIDEnums;
import com.redescooter.ses.api.common.enums.base.SystemIDEnums;
import com.redescooter.ses.api.common.enums.customer.CustomerCertificateTypeEnum;
import com.redescooter.ses.api.common.enums.customer.CustomerSourceEnum;
import com.redescooter.ses.api.common.enums.customer.CustomerStatusEnum;
import com.redescooter.ses.api.common.enums.customer.CustomerTypeEnum;
import com.redescooter.ses.api.common.enums.inquiry.InquiryPayStatusEnums;
import com.redescooter.ses.api.common.enums.inquiry.InquirySourceEnums;
import com.redescooter.ses.api.common.enums.inquiry.InquiryStatusEnums;
import com.redescooter.ses.api.common.enums.proxy.mail.MailTemplateEventEnums;
import com.redescooter.ses.api.common.enums.website.AccessoryTypeEnums;
import com.redescooter.ses.api.common.enums.website.ProductColorEnums;
import com.redescooter.ses.api.common.enums.website.ProductModelEnums;
import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.api.foundation.service.MailMultiTaskService;
import com.redescooter.ses.starter.common.config.SendinBlueConfig;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.DateUtil;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.tool.utils.accountType.RsaUtils;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.website.WebsiteInquiryServiceMapper;
import com.redescooter.ses.web.ros.dm.*;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.*;
import com.redescooter.ses.web.ros.service.customer.CustomerRosService;
import com.redescooter.ses.web.ros.service.monday.MondayService;
import com.redescooter.ses.web.ros.service.website.WebsiteOrderFormService;
import com.redescooter.ses.web.ros.vo.monday.enter.MondayBookOrderEnter;
import com.redescooter.ses.web.ros.vo.monday.enter.MondayGeneralEnter;
import com.redescooter.ses.web.ros.vo.website.*;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import okhttp3.*;
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
import java.util.*;
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

    @Autowired
    private MondayService mondayService;

    @Value("${Request.privateKey}")
    private String privatekey;

    @Autowired
    private SendinBlueConfig sendinBlueConfig;

    @Autowired
    private MailMultiTaskService mailMultiTaskService;

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
            resultList.add(ProductModelResult.builder().modelCode(item.getValue()).name(item.getMessage()).build());
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

        //判断当前客户已经为正式客户 如果为正式客户 不允许添加 预订单
        OpeSysUser opeSysUser = opeSysUserService.getById(enter.getUserId());
        if (opeSysUser == null) {
            throw new SesWebRosException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
        OpeCustomer opeCustomer = opeCustomerService.getOne(new LambdaQueryWrapper<OpeCustomer>().eq(OpeCustomer::getEmail, opeSysUser.getLoginName()).eq(OpeCustomer::getCustomerSource,
                CustomerSourceEnum.WEBSITE.getValue()));
        if (opeCustomer == null) {
            throw new SesWebRosException(ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getCode(), ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getMessage());
        }
        if (StringUtils.equals(opeCustomer.getStatus(), CustomerStatusEnum.OFFICIAL_CUSTOMER.getValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.CUSTOMER_NOT_ALLOWED_TO_CREATED_INQUIRY.getCode(), ExceptionCodeEnums.CUSTOMER_NOT_ALLOWED_TO_CREATED_INQUIRY.getMessage());
        }

        //订单校验客户订单校验 一个客户只允许存在一个订单
        List<OpeCustomerInquiry> customerInquiryList = opeCustomerInquiryService.list(new LambdaQueryWrapper<OpeCustomerInquiry>().eq(OpeCustomerInquiry::getEmail, opeCustomer.getEmail()));
        if (CollectionUtils.isNotEmpty(customerInquiryList)) {
            throw new SesWebRosException(ExceptionCodeEnums.CUSTOMER_ALREADY_EXIST_ORDER_FORM.getCode(), ExceptionCodeEnums.CUSTOMER_ALREADY_EXIST_ORDER_FORM.getMessage());
        }

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

        //附属配件总金额
        BigDecimal partsTotalPrice = checkBatteryQty(enter, product, battery.getPrice());

        //配件保存集合
        List<OpeCustomerInquiryB> opeCustomerInquiryBList = new ArrayList<>();
        //总价格计算
        BigDecimal totalPrice = product.getPrice().add(partsTotalPrice);
        //是否购买后备箱
        if (enter.getBuyTopCase()) {
            totalPrice = totalPrice.add(topCase.getPrice());
        }

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
        opeCustomerInquiry.setSource("2");
        opeCustomerInquiryService.save(opeCustomerInquiry);

        //发送数据到Monday
        mondayData(product.getColor(), enter.getAccessoryBatteryQty(), product.getProductModel(), opeCustomerInquiry);
        return SaveOrderFormResult.builder().id(opeCustomerInquiry.getId()).build();
    }

    /**
     * 发送数据到Monday
     *
     * @param productModel
     * @param opeCustomerInquiry
     */
    private void mondayData(String productColor, int batteryQty, String productModel, OpeCustomerInquiry opeCustomerInquiry) {
        MondayGeneralEnter mondayGeneralEnter = new MondayGeneralEnter();
        mondayGeneralEnter.setFirstName(opeCustomerInquiry.getFirstName());
        mondayGeneralEnter.setLastName(opeCustomerInquiry.getLastName());
        mondayGeneralEnter.setTelephone(opeCustomerInquiry.getTelephone());
        mondayGeneralEnter.setCreatedTime(new Date());
        mondayGeneralEnter.setUpdatedTime(new Date());
        mondayGeneralEnter.setEmail(opeCustomerInquiry.getEmail());
        mondayGeneralEnter.setCity(opeCustomerInquiry.getDef2());
        mondayGeneralEnter.setDistant(String.valueOf(opeCustomerInquiry.getDistrict()));
        mondayGeneralEnter.setRemarks(opeCustomerInquiry.getRemarks());
        mondayGeneralEnter.setAddress(opeCustomerInquiry.getAddress());
        MondayBookOrderEnter mondayBookOrderEnter = new MondayBookOrderEnter();
        mondayBookOrderEnter.setProductColor(ProductColorEnums.getProductColorEnumsByValue(productColor).getMessage());
        mondayBookOrderEnter.setBatteryQty(batteryQty);
        mondayBookOrderEnter.setProducModeltName(ProductModelEnums.getProductModelEnumsByValue(productModel).getMessage());
        mondayBookOrderEnter.setQty(1);
        mondayGeneralEnter.setT(mondayBookOrderEnter);

        //Monday 同步数据
        mondayService.websiteBookOrder(mondayGeneralEnter);
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
        List<OpeCustomerInquiry> customerInquiryList = opeCustomerInquiryService.list(new LambdaQueryWrapper<OpeCustomerInquiry>()
                .eq(OpeCustomerInquiry::getEmail, customerInquiry.getEmail())
                .gt(OpeCustomerInquiry::getId, customerInquiry.getId())
        );
        if (CollectionUtils.isNotEmpty(customerInquiryList)) {
            throw new SesWebRosException(ExceptionCodeEnums.CUSTOMER_ALREADY_EXIST_ORDER_FORM.getCode(), ExceptionCodeEnums.CUSTOMER_ALREADY_EXIST_ORDER_FORM.getMessage());
        }

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
        //附属配件总金额
        BigDecimal partsTotalPrice = checkBatteryQty(enter, product, battery.getPrice());

        //配件保存集合
        List<OpeCustomerInquiryB> opeCustomerInquiryBList = new ArrayList<>();
        //总价格计算
        BigDecimal totalPrice = product.getPrice().add(partsTotalPrice);
        //是否购买后备箱
        if (enter.getBuyTopCase()) {
            totalPrice = totalPrice.add(topCase.getPrice());
        }
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
        OpeSysUser opeSysUser = opeSysUserService.getById(enter.getUserId());
        if (opeSysUser == null) {
            throw new SesWebRosException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
        OpeCustomer opeCustomer = opeCustomerService.getOne(new LambdaQueryWrapper<OpeCustomer>().eq(OpeCustomer::getEmail, opeSysUser.getLoginName()).eq(OpeCustomer::getCustomerSource,
                CustomerSourceEnum.WEBSITE.getValue()));
        if (opeCustomer == null) {
            throw new SesWebRosException(ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getCode(), ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getMessage());
        }

        OpeCustomerInquiry opeCustomerInquiry = new OpeCustomerInquiry();
        opeCustomerInquiry.setId(id);
        opeCustomerInquiry.setDr(0);
        opeCustomerInquiry.setOrderNo(RandomStringUtils.randomAlphabetic(8));
        opeCustomerInquiry.setCustomerId(opeCustomer.getId());
        opeCustomerInquiry.setFirstName(SesStringUtils.upperCaseString(opeCustomer.getCustomerFirstName()));
        opeCustomerInquiry.setLastName(SesStringUtils.upperCaseString(opeCustomer.getCustomerLastName()));
        opeCustomerInquiry.setFullName(SesStringUtils.upperCaseString(opeCustomer.getCustomerFirstName()) + SesStringUtils.upperCaseString(opeCustomer.getCustomerLastName()));
        opeCustomerInquiry.setEmail(opeCustomer.getEmail());
        opeCustomerInquiry.setTelephone(StringUtils.isNotBlank(opeCustomer.getTelephone()) ? opeCustomer.getTelephone() : null);
        opeCustomerInquiry.setCustomerSource(CustomerSourceEnum.WEBSITE.getValue());
        opeCustomerInquiry.setStatus(InquiryStatusEnums.UNPAY_DEPOSIT.getValue());
        opeCustomerInquiry.setProductId(enter.getProductId());
        opeCustomerInquiry.setProductModel(enter.getProductModel());

        /**
         * 总价格
         */
        opeCustomerInquiry.setTotalPrice(totalPrice);
        /**
         * 产品单价
         */
        opeCustomerInquiry.setProductPrice(product.getPrice());
        /**
         * 已付金额
         */
        opeCustomerInquiry.setAmountPaid(new BigDecimal(0));
        /**
         * 待付金额
         */
        opeCustomerInquiry.setAmountObligation(totalPrice);
        /**
         * 预定金
         */
        opeCustomerInquiry.setPrepaidDeposit(new BigDecimal(590));
        /**
         * 优惠抵扣金额
         */
        opeCustomerInquiry.setAmountDiscount(new BigDecimal(0));


        if (StringUtils.isNotEmpty(opeCustomer.getDef1())) {
            opeCustomerInquiry.setCountry(opeCustomer.getDef1());
        }
        if (StringUtils.isNotEmpty(opeCustomer.getDef2())) {
            opeCustomerInquiry.setCity(opeCustomer.getDef2());
        }
        /** 邮政编码 **/
        if (StringUtils.isNotEmpty(opeCustomer.getDef3())) {
            opeCustomerInquiry.setPostCode(opeCustomer.getDef3());
        }
        if (StringUtils.isNotEmpty(opeCustomer.getAddress())) {
            opeCustomerInquiry.setAddress(opeCustomer.getAddress());
        }

        opeCustomerInquiry.setScooterQuantity(1);
        opeCustomerInquiry.setPayStatus(InquiryPayStatusEnums.UNPAY_DEPOSIT.getValue());
        opeCustomerInquiry.setTelephone(opeCustomer.getTelephone());
        opeCustomerInquiry.setBankCardName(enter.getBankCardName());
        opeCustomerInquiry.setSource(InquirySourceEnums.ORDER_FORM.getValue());
        opeCustomerInquiry.setCardNum(StringUtils.isEmpty(enter.getCardNum()) ? null : enter.getCardNum());
        opeCustomerInquiry.setExpiredTime(enter.getExpiredTime() != null && enter.getExpiredTime() != 0 ? DateUtil.timeStampToDate(enter.getExpiredTime(), DateUtil.UTC) : null);
        opeCustomerInquiry.setCvv(StringUtils.isBlank(enter.getCvv()) ? null : enter.getCvv());
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
                .expiredTime(customerInquiry.getExpiredTime())
                .color(opePartsProduct.getColor())
                .productPrice(customerInquiry.getProductPrice())
                .amountPaid(customerInquiry.getAmountPaid())
                .amountObligation(customerInquiry.getAmountObligation() == null ? new BigDecimal("0") : customerInquiry.getAmountObligation())
                .prepaidDeposit(customerInquiry.getPrepaidDeposit())
                .amountObligation(customerInquiry.getAmountObligation())
                .status(customerInquiry.getStatus())
                .totalPrice(customerInquiry.getTotalPrice())
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
    public GeneralResult payLastParagraph(IdEnter enter) {
        OpeCustomerInquiry customerInquiry = opeCustomerInquiryService.getById(enter.getId());
        if (customerInquiry == null) {
            throw new SesWebRosException(ExceptionCodeEnums.INQUIRY_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.INQUIRY_IS_NOT_EXIST.getMessage());
        }
        if (!StringUtils.equals(customerInquiry.getStatus(), InquiryStatusEnums.PAY_DEPOSIT.getValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.STATUS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_ILLEGAL.getMessage());
        }
        customerInquiry.setStatus(InquiryStatusEnums.PAY_DEPOSIT.getValue());
        customerInquiry.setPayStatus(InquiryPayStatusEnums.PAY_LAST_PARAGRAPH.getValue());
        customerInquiry.setUpdatedTime(new Date());
        opeCustomerInquiryService.updateById(customerInquiry);

        OpeCustomer opeCustomer = opeCustomerService.getById(customerInquiry.getCustomerId());
        if (opeCustomer == null) {
            throw new SesWebRosException(ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getCode(), ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getMessage());
        }
        if (!StringUtils.equals(opeCustomer.getStatus(), CustomerStatusEnum.POTENTIAL_CUSTOMERS.getValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.STATUS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_ILLEGAL.getMessage());
        }
        //校验客户信息
        checkCustomer(opeCustomer);
        opeCustomer.setStatus(CustomerStatusEnum.OFFICIAL_CUSTOMER.getValue());
        opeCustomer.setUpdatedTime(new Date());
        //todo 客户信息暂未更新
        return new GeneralResult();
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
        OpeCustomer opeCustomer = opeCustomerService.getOne(new LambdaQueryWrapper<OpeCustomer>().eq(OpeCustomer::getEmail, opeSysUser.getLoginName()).eq(OpeCustomer::getCustomerSource,
                CustomerSourceEnum.WEBSITE.getValue()));
        if (opeCustomer == null) {
            throw new SesWebRosException(ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getCode(), ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getMessage());
        }

        return CustomerInfoResult.builder()
                .id(opeCustomer.getId())
                .email(opeCustomer.getEmail())
                .firstName(opeCustomer.getCustomerFirstName())
                .lastName(opeCustomer.getCustomerLastName())
                .address(opeCustomer.getAddress())
                .customerCountry(opeCustomer.getDef1())
                .district(opeCustomer.getDef3())
                .city(opeCustomer.getDef2())
                .countryId(opeCustomer.getCountry())
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
        return new GeneralResult(enter.getRequestId());
    }

    private OpeCustomerInquiryB buildAccessory(SaveSaleOrderEnter enter, Long id, BigDecimal price, String type) {
        OpeCustomerInquiryB opeCustomerInquiryB = new OpeCustomerInquiryB();
        opeCustomerInquiryB.setId(idAppService.getId(SequenceName.OPE_CUSTOMER_INQUIRY_B));
        opeCustomerInquiryB.setDr(0);
        opeCustomerInquiryB.setInquiryId(id);
        opeCustomerInquiryB.setProductId(StringUtils.equals(type, AccessoryTypeEnums.TOP_CASE.getValue()) == true ? enter.getTopCaseId() : enter.getAccessoryBatteryId());
        opeCustomerInquiryB.setProductPrice(price);
        //记录电池数量
        opeCustomerInquiryB.setProductQty(enter.getAccessoryBatteryQty());
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
     * 返回所选电池价格
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
                qty = enter.getAccessoryBatteryQty() - 4;
                break;
        }

        return batteryPrice.multiply(new BigDecimal(qty));
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


        BaseMailTaskEnter enter = new BaseMailTaskEnter();
        enter.setEvent(MailTemplateEventEnums.SUBSCRIBE_TO_EMAIL_SUCCESSFULLY.getEvent());
        enter.setMailSystemId(AppIDEnums.SES_ROS.getSystemId());
        enter.setMailAppId(SystemIDEnums.REDE_SES.getValue());
        enter.setToMail(email);
        enter.setCode("0");
        enter.setRequestId("0");
        enter.setUserRequestId("0");
        enter.setToUserId(0L);
        enter.setUserId(0L);
        mailMultiTaskService.subscribeToEmailSuccessfully(enter);

        //数据同步Monday
        mondayService.websiteSubscriptionEmail(email);
    }

    /**
     * 校验客户信息
     *
     * @param enter
     */
    private void checkCustomer(OpeCustomer enter) {
        if (enter.getCity() == null) {
            throw new SesWebRosException(ExceptionCodeEnums.CITY_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.CITY_CANNOT_EMPTY.getMessage());
        }
        if (enter.getCustomerType().equals(CustomerTypeEnum.PERSONAL.getValue())) {

            if (StringUtils.isBlank(enter.getCustomerFirstName())) {
                throw new SesWebRosException(ExceptionCodeEnums.FIRST_NAME_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.FIRST_NAME_CANNOT_EMPTY.getMessage());
            }
            if (StringUtils.isBlank(enter.getCustomerLastName())) {
                throw new SesWebRosException(ExceptionCodeEnums.LAST_NAME_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.LAST_NAME_CANNOT_EMPTY.getMessage());
            }
        } else {
            if (StringUtils.isBlank(enter.getCompanyName())) {
                throw new SesWebRosException(ExceptionCodeEnums.COMPANY_NAME_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.COMPANY_NAME_CANNOT_EMPTY.getMessage());
            }
            if (StringUtils.isBlank(enter.getBusinessLicenseNum())) {
                throw new SesWebRosException(ExceptionCodeEnums.BUSINESS_LICENSE_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.BUSINESS_LICENSE_CANNOT_EMPTY.getMessage());
            }
            if (StringUtils.isBlank(enter.getBusinessLicenseNum())) {
                throw new SesWebRosException(ExceptionCodeEnums.BUSINESS_LICENSE_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.BUSINESS_LICENSE_CANNOT_EMPTY.getMessage());
            }
            if (StringUtils.isBlank(enter.getBusinessLicenseAnnex())) {
                throw new SesWebRosException(ExceptionCodeEnums.BUSINESS_LICENSE_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.BUSINESS_LICENSE_CANNOT_EMPTY.getMessage());
            }
        }
        if (StringUtils.isBlank(enter.getCustomerType())) {
            throw new SesWebRosException(ExceptionCodeEnums.CUSTOMER_TYPE_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.CUSTOMER_TYPE_CANNOT_EMPTY.getMessage());
        }
        if (StringUtils.isBlank(enter.getIndustryType())) {
            throw new SesWebRosException(ExceptionCodeEnums.INDUSTRY_TYPE_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.INDUSTRY_TYPE_CANNOT_EMPTY.getMessage());
        }
        if (StringUtils.isBlank(enter.getAddress())) {
            throw new SesWebRosException(ExceptionCodeEnums.ADDRESS_TYPE_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.ADDRESS_TYPE_CANNOT_EMPTY.getMessage());
        }
        if (StringUtils.isAllBlank(String.valueOf(enter.getLongitude()), String.valueOf(enter.getLatitude()))) {
            throw new SesWebRosException(ExceptionCodeEnums.LATITUDE_AND_LONGITUDE_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.LATITUDE_AND_LONGITUDE_CANNOT_EMPTY.getMessage());
        }
        if (StringUtils.isBlank(enter.getTelephone())) {
            throw new SesWebRosException(ExceptionCodeEnums.TELEPHONE_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.TELEPHONE_CANNOT_EMPTY.getMessage());
        }
        if (StringUtils.isBlank(enter.getCertificateType())) {
            throw new SesWebRosException(ExceptionCodeEnums.CERTIFICATETYPE_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.CERTIFICATETYPE_CANNOT_EMPTY.getMessage());
        }
        if (enter.getCertificateType().equals(CustomerCertificateTypeEnum.ID_CARD.getValue())) {
            if (StringUtils.isAllBlank(enter.getCertificatePositiveAnnex(), enter.getCertificateNegativeAnnex())) {
                throw new SesWebRosException(ExceptionCodeEnums.ID_CARD_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.ID_CARD_CANNOT_EMPTY.getMessage());
            }
        } else {
            if (StringUtils.isBlank(enter.getCertificatePositiveAnnex())) {
                throw new SesWebRosException(ExceptionCodeEnums.CERTIFICATE_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.CERTIFICATE_CANNOT_EMPTY.getMessage());
            }
        }
        if (StringUtils.isBlank(enter.getInvoiceNum())) {
            throw new SesWebRosException(ExceptionCodeEnums.INVOICE_NUM_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.INVOICE_NUM_CANNOT_EMPTY.getMessage());
        }
        if (StringUtils.isBlank(enter.getInvoiceAnnex())) {
            throw new SesWebRosException(ExceptionCodeEnums.INVOICE_ANNEX_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.INVOICE_ANNEX_CANNOT_EMPTY.getMessage());
        }
        if (StringUtils.isBlank(enter.getContractAnnex())) {
            throw new SesWebRosException(ExceptionCodeEnums.CONTRACT_ANNEX_CANNOT_EMPTY.getCode(), ExceptionCodeEnums.CONTRACT_ANNEX_CANNOT_EMPTY.getMessage());
        }

    }
}
