package com.redescooter.ses.web.ros.service.website.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.redescooter.ses.api.common.enums.customer.CustomerSourceEnum;
import com.redescooter.ses.api.common.enums.inquiry.InquiryPayStatusEnums;
import com.redescooter.ses.api.common.enums.inquiry.InquiryStatusEnums;
import com.redescooter.ses.api.common.enums.website.AccessoryTypeEnums;
import com.redescooter.ses.api.common.enums.website.ProductModelEnums;
import com.redescooter.ses.api.common.vo.base.BooleanResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.IntResult;
import com.redescooter.ses.api.common.vo.base.StringEnter;
import com.redescooter.ses.api.hub.service.operation.CustomerService;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.DateUtil;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.website.WebsiteInquiryServiceMapper;
import com.redescooter.ses.web.ros.dm.OpeCustomer;
import com.redescooter.ses.web.ros.dm.OpeCustomerAccessories;
import com.redescooter.ses.web.ros.dm.OpeCustomerInquiry;
import com.redescooter.ses.web.ros.dm.OpeCustomerInquiryB;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeCustomerInquiryBService;
import com.redescooter.ses.web.ros.service.base.OpeCustomerInquiryService;
import com.redescooter.ses.web.ros.service.base.OpeCustomerService;
import com.redescooter.ses.web.ros.service.base.OpePartsProductService;
import com.redescooter.ses.web.ros.service.base.impl.OpeCustomerAccessoriesService;
import com.redescooter.ses.web.ros.service.customer.CustomerRosService;
import com.redescooter.ses.web.ros.service.website.WebsiteOrderFormService;
import com.redescooter.ses.web.ros.vo.website.AccessoryResult;
import com.redescooter.ses.web.ros.vo.website.OrderFormsResult;
import com.redescooter.ses.web.ros.vo.website.OrderFormInfoResult;
import com.redescooter.ses.web.ros.vo.website.OrderFormsEnter;
import com.redescooter.ses.web.ros.vo.website.ProductModelResult;
import com.redescooter.ses.web.ros.vo.website.ProductResult;
import com.redescooter.ses.web.ros.vo.website.SaveOrderFormResult;
import com.redescooter.ses.web.ros.vo.website.SaveSaleOrderEnter;
import com.redescooter.ses.web.ros.vo.website.ScootersEnter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
     * 车辆型号
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
        //后备箱 校验
        OpeCustomerAccessories topCase = opeCustomerAccessoriesService.getById(enter.getTopCaseId());
        if (topCase == null) {
            throw new SesWebRosException(ExceptionCodeEnums.TOP_CASE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.TOP_CASE_IS_NOT_EXIST.getMessage());
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

        //配件保存集合
        List<OpeCustomerInquiryB> opeCustomerInquiryBList = new ArrayList<>();
        //总价格计算
        BigDecimal totalPrice = product.getPrice().add(battery.getPrice().multiply(new BigDecimal(enter.getAccessoryBatteryQty())));

        if (enter.getTopCaseId() != 0 && enter.getTopCaseId() != null) {
            totalPrice = product.getPrice().add(battery.getPrice().multiply(new BigDecimal(enter.getAccessoryBatteryQty()))).add(topCase.getPrice());
        }

        //生成主订单
        OpeCustomerInquiry opeCustomerInquiry = buildOpeCustomerInquiry(enter, product, totalPrice, idAppService.getId(SequenceName.OPE_CUSTOMER_INQUIRY));
        opeCustomerInquiry.setCreatedBy(enter.getUserId());
        opeCustomerInquiry.setCreatedTime(new Date());

        //生成子订单
        if (enter.getTopCaseId() != 0 && enter.getTopCaseId() != null) {
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
        //后备箱 校验
        OpeCustomerAccessories topCase = opeCustomerAccessoriesService.getById(enter.getTopCaseId());
        if (topCase == null) {
            throw new SesWebRosException(ExceptionCodeEnums.TOP_CASE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.TOP_CASE_IS_NOT_EXIST.getMessage());
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

        //配件保存集合
        List<OpeCustomerInquiryB> opeCustomerInquiryBList = new ArrayList<>();
        //总价格计算
        BigDecimal totalPrice = product.getPrice().add(battery.getPrice().multiply(new BigDecimal(enter.getAccessoryBatteryQty())));

        if (enter.getTopCaseId() != 0 && enter.getTopCaseId() != null) {
            totalPrice = product.getPrice().add(battery.getPrice().multiply(new BigDecimal(enter.getAccessoryBatteryQty()))).add(topCase.getPrice());
        }
        //生成主订单
        OpeCustomerInquiry opeCustomerInquiry = buildOpeCustomerInquiry(enter, product, totalPrice, enter.getId());
        opeCustomerInquiry.setCreatedBy(enter.getUserId());
        opeCustomerInquiry.setCreatedTime(new Date());

        //生成子订单
        if (enter.getTopCaseId() != 0 && enter.getTopCaseId() != null) {
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
        opeCustomerInquiry.setFirstName(opeCustomer.getCustomerFirstName());
        opeCustomerInquiry.setLastName(opeCustomer.getCustomerLastName());
        opeCustomerInquiry.setFullName(opeCustomer.getCustomerFullName());
        opeCustomerInquiry.setEmail(opeCustomer.getEmail());
        opeCustomerInquiry.setCustomerSource(CustomerSourceEnum.WEBSITE.getValue());
        opeCustomerInquiry.setStatus(InquiryStatusEnums.UNPROCESSED.getValue());
        opeCustomerInquiry.setProductId(enter.getProductId());
        opeCustomerInquiry.setProductModel(enter.getProductModel());
        opeCustomerInquiry.setProductPrice(product.getPrice());
        opeCustomerInquiry.setTotalPrice(totalPrice);
        opeCustomerInquiry.setScooterQuantity(enter.getProductQty());
        opeCustomerInquiry.setPayStatus(InquiryPayStatusEnums.UNPAY_DEPOSIT.getValue());
        opeCustomerInquiry.setAddress(enter.getAddress());
        opeCustomerInquiry.setCountryCode(enter.getCountryCode());
        opeCustomerInquiry.setTelephone(enter.getPhone());
        opeCustomerInquiry.setBankCardName(enter.getBankCardName());
        opeCustomerInquiry.setCardNum(enter.getCardNum());
        opeCustomerInquiry.setExpiredTime(DateUtil.timeStampToDate(enter.getExpiredTime(),DateUtil.UTC));
        opeCustomerInquiry.setCvv(enter.getCvv());
        opeCustomerInquiry.setPostalCode(enter.getPostalCode());
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

        //查询子订单
        List<OpeCustomerInquiryB> inquiryBList = opeCustomerInquiryBService.list(new LambdaQueryWrapper<OpeCustomerInquiryB>().eq(OpeCustomerInquiryB::getInquiryId, customerInquiry.getId()));
        if (CollectionUtils.isEmpty(inquiryBList)) {
            return null;
        }
        //反参对象
        OrderFormInfoResult result = OrderFormInfoResult.builder()
                .id(customerInquiry.getId())
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
    public BooleanResult checkMail(StringEnter enter) {
        IntResult checkMailCount = customerRosService.checkMailCount(enter);
        return BooleanResult.builder().success(checkMailCount.getValue() == 0 ? Boolean.TRUE : Boolean.FALSE).build();
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
}
