package com.redescooter.ses.web.website.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.constant.DateConstant;
import com.redescooter.ses.api.common.enums.restproductionorder.OrderNumberTypeEnums;
import com.redescooter.ses.api.common.service.SiteWebInquiryService;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.inquiry.SiteWebInquiryEnter;
import com.redescooter.ses.api.common.vo.inquiry.SiteWebInquiryPriceEnter;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.OrderNoGenerateUtil;
import com.redescooter.ses.tool.utils.date.DateUtil;
import com.redescooter.ses.web.website.constant.SequenceName;
import com.redescooter.ses.web.website.dao.OrderMapper;
import com.redescooter.ses.web.website.dao.ScooterPurchaseMapper;
import com.redescooter.ses.web.website.dm.SiteCustomer;
import com.redescooter.ses.web.website.dm.SiteOrder;
import com.redescooter.ses.web.website.dm.SiteOrderB;
import com.redescooter.ses.web.website.dm.SiteParts;
import com.redescooter.ses.web.website.dm.SitePaymentType;
import com.redescooter.ses.web.website.dm.SiteProduct;
import com.redescooter.ses.web.website.dm.SiteProductColour;
import com.redescooter.ses.web.website.dm.SiteProductParts;
import com.redescooter.ses.web.website.dm.SiteProductPrice;
import com.redescooter.ses.web.website.dm.SiteUser;
import com.redescooter.ses.web.website.enums.DeliveryMethodEnums;
import com.redescooter.ses.web.website.enums.SiteOrderPaymentStatusEnums;
import com.redescooter.ses.web.website.enums.SiteOrderStatusEnums;
import com.redescooter.ses.web.website.enums.SiteOrderTypeEnums;
import com.redescooter.ses.web.website.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.website.exception.SesWebsiteException;
import com.redescooter.ses.web.website.service.OrderService;
import com.redescooter.ses.web.website.service.base.SiteCustomerService;
import com.redescooter.ses.web.website.service.base.SiteOrderBService;
import com.redescooter.ses.web.website.service.base.SiteOrderService;
import com.redescooter.ses.web.website.service.base.SitePartsService;
import com.redescooter.ses.web.website.service.base.SitePaymentTypeService;
import com.redescooter.ses.web.website.service.base.SiteProductColourService;
import com.redescooter.ses.web.website.service.base.SiteProductPartsService;
import com.redescooter.ses.web.website.service.base.SiteProductPriceService;
import com.redescooter.ses.web.website.service.base.SiteProductService;
import com.redescooter.ses.web.website.service.base.SiteUserService;
import com.redescooter.ses.web.website.vo.order.AddOrderPartsEnter;
import com.redescooter.ses.web.website.vo.order.AddOrderResult;
import com.redescooter.ses.web.website.vo.order.AddPartListEnter;
import com.redescooter.ses.web.website.vo.order.AddUpdateOrderEnter;
import com.redescooter.ses.web.website.vo.order.OrderDetailsResult;
import com.redescooter.ses.web.website.vo.product.PartsBatteryDetailsResult;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author jerry
 * @Date 2021/1/9 7:19 下午
 * @Description 订单服务
 **/
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private SiteOrderService siteOrderService;

    @Autowired
    private SiteOrderBService siteOrderBService;

    @Autowired
    private SiteUserService siteUserService;

    @Autowired
    private SiteCustomerService siteCustomerService;

    @Autowired
    private SiteProductService siteProductService;

    @Autowired
    private SiteProductPartsService siteProductPartsService;

    @Autowired
    private SitePartsService sitePartsService;

    @Autowired
    private SiteProductPriceService siteProductPriceService;

    @Autowired
    private SiteProductColourService siteProductColourService;

    @Autowired
    private SitePaymentTypeService sitePaymentTypeService;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ScooterPurchaseMapper scooterPurchaseMapper;

    @DubboReference
    private IdAppService idAppService;

    @DubboReference
    private SiteWebInquiryService siteWebInquiryService;

    /**
     * 创建订单
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public AddOrderResult addOrder(AddUpdateOrderEnter enter) {
        SiteOrder addSiteOrderVO = null;
        //获取当前登录用户
        SiteUser user = siteUserService.getById(enter.getUserId());

        if (user == null || enter.getColourId() == null || enter.getProductId() == null) {
            throw new SesWebsiteException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(),
                    ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }

        String email = user.getLoginName();

        //获取客户
        SiteCustomer customer = siteCustomerService.getById(user.getCustomerId());
        if (customer == null) {
            throw new SesWebsiteException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(),
                    ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
        if (enter.getOrderId() == null || enter.getOrderId() == 0L) {
            //创建订单
            //查询该用户是否有订单
            List<SiteOrder> orderSize = siteOrderService.list(new QueryWrapper<SiteOrder>()
                    .eq(SiteOrder.COL_CUSTOMER_ID, customer.getId())
                    .eq(SiteOrder.COL_DR, Constant.DR_FALSE));
            if (orderSize.size() != 0) {
                throw new SesWebsiteException(ExceptionCodeEnums.UN_COMPLETE_ORDER_ERROR.getCode(),
                        ExceptionCodeEnums.UN_COMPLETE_ORDER_ERROR.getMessage());
            }
            addSiteOrderVO = new SiteOrder();
            addSiteOrderVO.setId(idAppService.getId(SequenceName.SITE_PARTS));
            /**创建订单编号，临时编写的**/
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddss");
            String format = dateFormat.format(calendar.getTime());
//            String orderNo = new StringBuffer().append("RED").append(format).append(String.valueOf(idAppService.getId(SequenceName.SITE_ORDER)).substring(5)).toString();
//            addSiteOrderVO.setOrderNo(orderNo);
            addSiteOrderVO.setOrderNo(createOrderNo(OrderNumberTypeEnums.INQUIRY_ORDER.getValue()));
            addSiteOrderVO.setCustomerId(user.getCustomerId());
            addSiteOrderVO.setSalesId(0L);
            addSiteOrderVO.setDr(Constant.DR_FALSE);
            addSiteOrderVO.setStatus(SiteOrderStatusEnums.NEWS.getValue());

        } else {
            //更新订单
            addSiteOrderVO = siteOrderService.getById(enter.getOrderId());
            if (addSiteOrderVO == null) {
                throw new SesWebsiteException(ExceptionCodeEnums.ORDER_NOT_EXIST_EXIST.getCode(),
                        ExceptionCodeEnums.ORDER_NOT_EXIST_EXIST.getMessage());
            }
            if (addSiteOrderVO.getStatus().equals(SiteOrderStatusEnums.IN_PROGRESS.getValue())
                    || addSiteOrderVO.getStatus().equals(SiteOrderStatusEnums.COMPLETED.getValue())) {
                throw new SesWebsiteException(ExceptionCodeEnums.WRONG_ORDER_STATUS.getCode(),
                        ExceptionCodeEnums.WRONG_ORDER_STATUS.getMessage());
            }
        }

        SitePaymentType paymentType = sitePaymentTypeService.getById(enter.getPaymentTypeId());
        if (paymentType == null) {
            throw new SesWebsiteException(ExceptionCodeEnums.PAYMENTTYPE_NOT_EXIST_EXIST.getCode(),
                    ExceptionCodeEnums.PAYMENTTYPE_NOT_EXIST_EXIST.getMessage());
        }
        //获取产品
        //SiteProduct product = siteProductService.getById(enter.getProductId());
        LambdaQueryWrapper<SiteProduct> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SiteProduct::getDr, Constant.DR_FALSE);
        wrapper.eq(SiteProduct::getProductModelId, enter.getProductId());
        wrapper.last("limit 1");
        SiteProduct product = siteProductService.getOne(wrapper);
        if (product == null) {
            throw new SesWebsiteException(ExceptionCodeEnums.PRODUCT_NOT_EXIST_EXIST.getCode(),
                    ExceptionCodeEnums.PRODUCT_NOT_EXIST_EXIST.getMessage());
        }
        //获取产品颜色
        SiteProductColour productColour = siteProductColourService.getOne(new QueryWrapper<SiteProductColour>()
                .eq(SiteProductColour.COL_COLOUR_ID, enter.getColourId())
                .eq(SiteProductColour.COL_PRODUCT_ID, enter.getProductId()));

        if (productColour == null) {
            throw new SesWebsiteException(ExceptionCodeEnums.COLOR_NOT_EXIST_EXIST.getCode(),
                    ExceptionCodeEnums.COLOR_NOT_EXIST_EXIST.getMessage());
        }
        //获取产品价格
        Long id;
        String installmentTime = null;
        String paymentTypeId = enter.getPaymentTypeId();
        if (paymentTypeId.contains("+")) {
            id = Long.valueOf(paymentTypeId.substring(0, paymentTypeId.indexOf("+")));
            installmentTime = paymentTypeId.substring(paymentTypeId.indexOf("+") + 1);
        } else {
            id = Long.valueOf(paymentTypeId);
        }
        SitePaymentType type = sitePaymentTypeService.getById(id);
        String code = type.getPaymentCode();
        SitePaymentType sitePaymentType = sitePaymentTypeService.getById(id);
        LambdaQueryWrapper<SiteProductPrice> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(SiteProductPrice::getProductModelId, product.getProductModelId());
        queryWrapper1.eq(SiteProductPrice::getDr, Constant.DR_FALSE);
        queryWrapper1.eq(SiteProductPrice::getPriceType, sitePaymentType.getPaymentCode());
        if (StringUtils.isBlank(enter.getInstallmentTime())) {
            queryWrapper1.eq(SiteProductPrice::getInstallmentTime, 0);
        } else {
            queryWrapper1.eq(SiteProductPrice::getInstallmentTime, enter.getInstallmentTime());
        }
        SiteProductPrice siteProductPrice = siteProductPriceService.getOne(queryWrapper1);

        //获取所选车辆电池数量
//        SiteProductParts scooterBatteryParts = siteProductPartsService.getById(enter.getProductPartsId());
//        if (scooterBatteryParts == null) {
//            throw new SesWebsiteException(ExceptionCodeEnums.BATTERIES_NUM_ERROR.getCode(),
//                    ExceptionCodeEnums.BATTERIES_NUM_ERROR.getMessage());
//        }
        //获取电池
        PartsBatteryDetailsResult battery = scooterPurchaseMapper.getPartsDetails(new GeneralEnter());
        //SiteParts battery = sitePartsService.getById(scooterBatteryParts.getPartsId());
        if (battery == null) {
            throw new SesWebsiteException(ExceptionCodeEnums.PARTS_NOT_EXIST_EXIST.getCode(),
                    ExceptionCodeEnums.PARTS_NOT_EXIST_EXIST.getMessage());
        }

        addSiteOrderVO.setOrderType(SiteOrderTypeEnums.checkValue(enter.getOrderType()));
        //配送方式
        addSiteOrderVO.setDeliveryType(DeliveryMethodEnums.checkValue(enter.getDeliveryType()));
        addSiteOrderVO.setDealerId(enter.getDealerId());

        addSiteOrderVO.setProductId(productColour.getProductId());
        addSiteOrderVO.setBatteryQty(enter.getBatteryQty());
        addSiteOrderVO.setColourId(productColour.getColourId());
        addSiteOrderVO.setFullName(customer.getCustomerFullName());
        addSiteOrderVO.setCountryName(customer.getCountryName());
        addSiteOrderVO.setCityName(customer.getCityName());
        addSiteOrderVO.setPostcode(customer.getPostcode());
        //这里是送货地址
        addSiteOrderVO.setAddress(enter.getDeliveryAddress());
        //运费
        addSiteOrderVO.setFreight(new BigDecimal("199"));
        //整车价格
        addSiteOrderVO.setProductPrice(siteProductPrice.getPrice());
        //实际付款电池数: 选配电池总数-产品最小电池数
        //int paidBattery = scooterBatteryParts.getQty() - product.getMinBatteryNum();
        int paidBattery = enter.getBatteryQty() - product.getMinBatteryNum();
        BigDecimal totalPrice = new BigDecimal("0");
        //根据付款类型id查询是什么付款方式


        LambdaQueryWrapper<SiteProduct> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SiteProduct::getId, addSiteOrderVO.getProductId());
        SiteProduct siteProduct = siteProductService.getOne(queryWrapper);
        if (product == null) {
            throw new SesWebsiteException(ExceptionCodeEnums.PRODUCT_NOT_EXIST_EXIST.getCode(),
                    ExceptionCodeEnums.PRODUCT_NOT_EXIST_EXIST.getMessage());
        }

        LambdaQueryWrapper<SiteParts> queryWrapper2 = new LambdaQueryWrapper<>();
        queryWrapper2.eq(SiteParts::getPartsType, 3);
        queryWrapper2.last("limit 1");
        SiteParts siteParts = sitePartsService.getOne(queryWrapper2);
        if (siteParts == null) {
            throw new SesWebsiteException(ExceptionCodeEnums.PARTS_NOT_EXIST_EXIST.getCode(),
                    ExceptionCodeEnums.PARTS_NOT_EXIST_EXIST.getMessage());
        }
        //判断是分期，租赁还是全款支付
        if (siteProductPrice.getPriceType() == 1 || siteProductPrice.getPriceType() == 3) {
            //还没有支付过的情况  //分期和租赁没选择其他配件的时候 只是定金+运费
            if (addSiteOrderVO.getDef1() == null) {
                String batterys = siteProductPrice.getBattery().substring(0, 1);
                BigDecimal batteryResult = new BigDecimal(enter.getBatteryQty()).subtract(new BigDecimal(batterys));
                BigDecimal batteryResult2 = new BigDecimal("0");
                batteryResult2 = batteryResult.multiply(siteParts.getPrice()).divide(new BigDecimal(siteProductPrice.getInstallmentTime()), 2, BigDecimal.ROUND_UP);
                BigDecimal installmentTime2 = new BigDecimal(siteProductPrice.getInstallmentTime());
                totalPrice = siteProductPrice.getPrepaidDeposit().add(new BigDecimal("199")).add(siteProductPrice.getShouldPayPeriod().multiply(installmentTime2)).add(batteryResult2.multiply(installmentTime2));
            } else {
                totalPrice = siteProductPrice.getShouldPayPeriod().add(new BigDecimal(String.valueOf(paidBattery)).divide(new BigDecimal(siteProductPrice.getInstallmentTime())));
            }
        } else {
            //没有选着其他配件前，只是整车的价格+运费
            totalPrice = new BigDecimal("199").add(siteProductPrice.getPrepaidDeposit()).add(siteProductPrice.getPrice().multiply(new BigDecimal(enter.getScooterQuantity())));
            //加上选购电池的价格
            totalPrice = totalPrice.add(battery.getPrice().multiply(new BigDecimal(String.valueOf(paidBattery))));
        }
        addSiteOrderVO.setTotalPrice(totalPrice);
        log.info("addSiteOrderVO.getTotalPrice()>>>>>>>>>>>>>>>>>>>:{}", addSiteOrderVO.getTotalPrice());
        //已付金额
        addSiteOrderVO.setAmountPaid(new BigDecimal("0"));
        //代付款金额
        addSiteOrderVO.setAmountObligation(addSiteOrderVO.getTotalPrice());
        //预付定金
        //addSiteOrderVO.setPrepaidDeposit(new BigDecimal("590"));
        addSiteOrderVO.setPrepaidDeposit(siteProductPrice.getPrepaidDeposit());
        //优惠抵扣金额
        addSiteOrderVO.setAmountDiscount(new BigDecimal("0"));
        //支付类型
        if (enter.getPaymentTypeId().contains("+")) {
            addSiteOrderVO.setPaymentTypeId(Long.valueOf(enter.getPaymentTypeId().substring(0, enter.getPaymentTypeId().indexOf("+"))));
        } else {
            addSiteOrderVO.setPaymentTypeId(Long.valueOf(enter.getPaymentTypeId()));
        }
        //支付状态
        addSiteOrderVO.setPayStatus(SiteOrderPaymentStatusEnums.UN_PAID.getValue());
        //购买车辆数
        addSiteOrderVO.setScooterQuantity(enter.getScooterQuantity());
        addSiteOrderVO.setSynchronizeFlag(false);
        addSiteOrderVO.setEtdDeliveryTime(this.getDateForDay(45));
        addSiteOrderVO.setRevision(0);
        addSiteOrderVO.setCreatedBy(enter.getUserId());
        addSiteOrderVO.setCreatedTime(new Date());
        addSiteOrderVO.setCountryName(enter.getCountryName());
        addSiteOrderVO.setCityName(enter.getCityName());
        addSiteOrderVO.setPostcode(enter.getPostcode());
        addSiteOrderVO.setUpdatedBy(enter.getUserId());
        //记录用户选择的期数
        addSiteOrderVO.setDef5(enter.getInstallmentTime());
        siteOrderService.saveOrUpdate(addSiteOrderVO);
        AddOrderResult result = new AddOrderResult();
        result.setId(addSiteOrderVO.getId());
        result.setDeposit(addSiteOrderVO.getPrepaidDeposit());
        result.setRequestId(enter.getRequestId());
        String bankCardName = customer.getCardholder();
        SiteOrder siteOrder = siteOrderService.getById(addSiteOrderVO.getId());
        String batterys = siteProductPrice.getBattery().substring(0, 1);
        BigDecimal batteryResult = new BigDecimal(siteOrder.getBatteryQty()).subtract(new BigDecimal(batterys));
        BigDecimal batteryResult2 = new BigDecimal("0");
        if (batteryResult.compareTo(BigDecimal.ZERO) == 0) {
            batteryResult2 = new BigDecimal("0");
            addSiteOrderVO.setTotalPrice(siteOrder.getTotalPrice().add(batteryResult2));
        } else {
            if (StringUtils.isBlank(enter.getInstallmentTime()) || "0".equals(enter.getInstallmentTime())) {
                batteryResult2 = new BigDecimal("0");
                //batteryResult2 = batteryResult.multiply(siteParts.getPrice());
                addSiteOrderVO.setTotalPrice(siteOrder.getTotalPrice().add(batteryResult2));
            } else {
                batteryResult2 = batteryResult.multiply(siteParts.getPrice()).divide(new BigDecimal(siteProductPrice.getInstallmentTime()), 2, BigDecimal.ROUND_UP);
                // BigDecimal peijian = new BigDecimal(siteOrder.getDef1()).divide(new BigDecimal(siteProductPrice.getInstallmentTime()), 2, BigDecimal.ROUND_UP).multiply(new BigDecimal(siteProductPrice.getInstallmentTime()));
                BigDecimal installmentTime2 = new BigDecimal(siteProductPrice.getInstallmentTime());
                addSiteOrderVO.setTotalPrice(siteOrder.getPrepaidDeposit().add(siteOrder.getFreight()).add(siteProductPrice.getShouldPayPeriod().multiply(installmentTime2)).add(batteryResult2.multiply(installmentTime2)));
                addSiteOrderVO.setAmountObligation(siteOrder.getTotalPrice().subtract(addSiteOrderVO.getAmountPaid()));
            }
        }
        // 官网的订单数据 要同步到ROS系统中
        syncdataToRos(addSiteOrderVO, email, bankCardName);

        return result;
    }

    /**
     * 询价单号生成规则
     */
    public String createOrderNo(String orderNoEnum) {
        String code = "";
        // 先判断当前的日期有没有生成过单据号
        QueryWrapper<SiteOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(SiteOrder.COL_ORDER_NO, DateUtil.getSimpleDateStamp());
        queryWrapper.like(SiteOrder.COL_ORDER_NO, "W");
        queryWrapper.orderByDesc(SiteOrder.COL_ORDER_NO);
        queryWrapper.last("limit 1");
        SiteOrder inquiry = siteOrderService.getOne(queryWrapper);
        if (inquiry != null) {
            // 说明今天已经有过单据了  只需要流水号递增
            code = OrderNoGenerateUtil.orderNoGenerate(inquiry.getOrderNo(), orderNoEnum);
        } else {
            // 说明今天还没有产生过单据号，给今天的第一个就好
            code = orderNoEnum + DateUtil.getSimpleDateStamp() + "001";
        }
        //生成订单号之后 在流水号前面加上W（表示官网的数据）
        String frond = code.substring(0, code.length() - 3);
        String back = code.substring(code.length() - 3, code.length());
        return frond + "W" + back;
    }


    /**
     * 这个方法要使用异步请求
     *
     * @param addSiteOrderVO
     */
    @Async
    public void syncdataToRos(SiteOrder addSiteOrderVO, String email, String bankCardName) {
        // 构造请求的参数
        SiteWebInquiryEnter enter = new SiteWebInquiryEnter();
        BeanUtils.copyProperties(addSiteOrderVO, enter);
        enter.setBankCardName(bankCardName);
        enter.setBatteryQty(addSiteOrderVO.getBatteryQty());
        enter.setAmountPaid(addSiteOrderVO.getAmountPaid());
        enter.setTotalPrice(addSiteOrderVO.getTotalPrice());
        enter.setAmountObligation(addSiteOrderVO.getAmountObligation());
        // 給productModel赋值
        String productModel = orderMapper.getProductModelByOrderId(addSiteOrderVO.getId());
        if (StringUtils.isNotBlank(productModel)) {
            enter.setProductModel(productModel);
        }
        // 调方法 同步数据
        log.info("开始同步");
        siteWebInquiryService.siteWebOrderToRosInquiry(enter, email);
        log.info("结束同步");

        // 同步之后要把同步表示改为已同步
        addSiteOrderVO.setSynchronizeFlag(true);
        siteOrderService.updateById(addSiteOrderVO);
    }


    /**
     * 创建订单配件
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult AddOrderParts(AddOrderPartsEnter enter) {
        if (null != enter && StringUtils.isNotBlank(enter.getPartslist()) && !"[]".equals(enter.getPartslist())) {
            // 解析
            List<AddPartListEnter> partslist;
            try {
                partslist = JSONArray.parseArray(enter.getPartslist(), AddPartListEnter.class);
            } catch (Exception ex) {
                throw new SesWebsiteException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
            }
            if (CollectionUtils.isEmpty(partslist)) {
                throw new SesWebsiteException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
            }

            Long orderId = enter.getOrderId();
            if (null == orderId || orderId == 0L) {
                throw new SesWebsiteException(ExceptionCodeEnums.ORDER_NOT_EXIST_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST_EXIST.getMessage());
            }
            //获取订单
            SiteOrder order = siteOrderService.getById(orderId);
            //获取配件
            if (partslist.size() == 0) {
                return new GeneralResult(enter.getRequestId());
            }

            //配件总金额计算
            BigDecimal partAllTotalPrice = new BigDecimal("0");
            SiteOrderB orderB = null;
            List<SiteOrderB> list = new ArrayList<>();
            for (AddPartListEnter p : partslist) {
                //获取配件信息
                SiteParts part = sitePartsService.getById(p.getPartsId());
                //如果没有查到配件，直接直接跳过本次循环
                if (part == null) {
                    continue;
                }
                orderB = new SiteOrderB();
                orderB.setId(idAppService.getId(SequenceName.SITE_ORDER_B));
                orderB.setDr(Constant.DR_FALSE);
                orderB.setOrderId(orderId);
                orderB.setProductId(order.getProductId());
                orderB.setPartsId(p.getPartsId());
                orderB.setPartsQty(p.getParts_qty());
                //单个配件价格和
                BigDecimal partPriceSun = part.getPrice().multiply(new BigDecimal(p.getParts_qty()));
                orderB.setPartsPrice(partPriceSun);
                orderB.setRevision(0);
                orderB.setCreatedBy(enter.getUserId());
                orderB.setCreatedTime(new Date());
                orderB.setUpdatedBy(enter.getUserId());
                orderB.setUpdatedTime(new Date());
                list.add(orderB);
                //价格累计
                partAllTotalPrice = partAllTotalPrice.add(partPriceSun);
            }

            // 不包含+,说明是分期支付和租赁
            BigDecimal shouldPayPeriod = new BigDecimal("0");
            SitePaymentType paymentType = sitePaymentTypeService.getById(enter.getPaymentTypeId());


            LambdaQueryWrapper<SiteProductPrice> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.eq(SiteProductPrice::getProductModelId, enter.getModelId());
            queryWrapper1.eq(SiteProductPrice::getDr, Constant.DR_FALSE);
            queryWrapper1.eq(SiteProductPrice::getPriceType, Integer.valueOf(paymentType.getPaymentCode()));
            queryWrapper1.eq(SiteProductPrice::getInstallmentTime, order.getDef5());
            SiteProductPrice productPrice = siteProductPriceService.getOne(queryWrapper1);

            if ("3".equals(paymentType.getPaymentCode()) || "1".equals(paymentType.getPaymentCode())) {
                if (null != productPrice) {
                    String installmentTime = productPrice.getInstallmentTime();
//                        Integer count = Integer.valueOf(installmentTime) - 1;
                    Integer count = Integer.valueOf(installmentTime);
                    // 配件总额  平均分到每期
                    shouldPayPeriod = shouldPayPeriod.add(partAllTotalPrice.divide(new BigDecimal(String.valueOf(count)), 2, BigDecimal.ROUND_UP));
                    log.info(shouldPayPeriod + "{>>>>>>>>>>>进入这里}");
                }
            }
            if (shouldPayPeriod.compareTo(new BigDecimal("0")) == 0) {
                log.info("没进预想的逻辑,此时配件应付金额等于全额");
                shouldPayPeriod = shouldPayPeriod.add(partAllTotalPrice);
            }

//            BigDecimal orderTotalPrice = order.getTotalPrice().add(partAllTotalPrice);
            BigDecimal orderTotalPrice = order.getTotalPrice();
            //更新总金额
            //order.setTotalPrice(orderTotalPrice);
            //更新待付金额
//            BigDecimal amountObligation = order.getTotalPrice().add(shouldPayPeriod);
//            order.setAmountObligation(amountObligation);
            order.setAmountObligation(order.getAmountObligation());
            //所选择配件总价格做备份记录
            order.setDef1(partAllTotalPrice.toPlainString());
            log.info("订单总价格：{}", order.getTotalPrice());
            if ("3".equals(paymentType.getPaymentCode()) || "1".equals(paymentType.getPaymentCode())) {
                order.setTotalPrice(orderTotalPrice.add(new BigDecimal(String.valueOf(shouldPayPeriod)).multiply(new BigDecimal(productPrice.getInstallmentTime()))));
            } else {
                order.setTotalPrice(orderTotalPrice.add(shouldPayPeriod));
                log.info("order.setTotalPrice>>>>>>>>>>>>>>>>>>>>>{}", order.getTotalPrice());
                log.info("shouldPayPeriod>>>>>>>>>>>>>>>>>>>>>>>>>{}", shouldPayPeriod);
            }

            log.info("订单总价格new>>>>>>>>>>>>>>>>>>>>>：{}", order.getTotalPrice());
            order.setAmountObligation(order.getAmountObligation().add(new BigDecimal(String.valueOf(shouldPayPeriod))));
            //如果是分期的情况配件每期应付的价格
            orderB.setDef2(String.valueOf(shouldPayPeriod));
            //更新主订单金额
            siteOrderService.updateById(order);
            //创建子订单
            siteOrderBService.batchInsert(list);

            // 如果选择的有配件,同步修改ros预订单的价格(加上配件的价格)
            syncPriceToRos(enter, order.getTotalPrice(), order.getTotalPrice().subtract(order.getAmountPaid()));
        }
        return new GeneralResult(enter.getRequestId());
    }

    public void syncPriceToRos(AddOrderPartsEnter enter, BigDecimal totalPrice, BigDecimal amountObligation) {
        // 获取当前登录用户
        SiteUser user = siteUserService.getById(enter.getUserId());
        if (null == user) {
            throw new SesWebsiteException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
        String email = user.getLoginName();

        // 封装参数
        SiteWebInquiryPriceEnter model = new SiteWebInquiryPriceEnter();
        model.setTotalPrice(totalPrice);
        model.setAmountObligation(amountObligation);
        siteWebInquiryService.updateRosInquiryPrice(model, email);
    }

    /**
     * 客户订单列表
     *
     * @param enter
     * @return
     */
    @Override
    public List<OrderDetailsResult> getOrderList(GeneralEnter enter) {

        SiteUser user = siteUserService.getById(enter.getUserId());
        if (user == null) {
            throw new SesWebsiteException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(),
                    ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
        SiteCustomer customer = siteCustomerService.getById(user.getCustomerId());
        IdEnter idEnter = new IdEnter();
        idEnter.setId(customer.getId());
        List<OrderDetailsResult> orderlist = orderMapper.getOrderlist(idEnter);
        return orderlist;
    }

    /**
     * 获取订单详情
     *
     * @param enter
     */
    @Override
    public OrderDetailsResult getOrderDetails(IdEnter enter) {

        SiteUser user = siteUserService.getById(enter.getUserId());
        if (user == null) {
            throw new SesWebsiteException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(),
                    ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
        ;
        OrderDetailsResult orderDetails = orderMapper.getOrderDetails(enter);
        orderDetails.setRequestId(enter.getRequestId());
        return orderDetails;
    }


    /**
     * 删除订单
     *
     * @param enter
     * @return
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult deleteOrder(IdEnter enter) {
        SiteOrder siteOrder = siteOrderService.getById(enter.getId());
        if (siteOrder == null) {
            throw new SesWebsiteException(ExceptionCodeEnums.ORDER_NOT_EXIST_EXIST.getCode(), ExceptionCodeEnums.ORDER_NOT_EXIST_EXIST.getMessage());
        }
        siteOrderService.removeById(enter.getId());
        QueryWrapper<SiteOrderB> qw = new QueryWrapper<>();
        qw.eq(SiteOrderB.COL_ORDER_ID, enter.getId());
        List<SiteOrderB> orderBS = siteOrderBService.list(qw);
        if (CollectionUtils.isNotEmpty(orderBS)) {
            siteOrderBService.removeByIds(orderBS.stream().map(SiteOrderB::getId).collect(Collectors.toList()));
        }
        // 官网数据删除  需要把ROS那边对应的订单删除
        try {
            deleRosOrder(siteOrder);
        } catch (Exception e) {

        }
        return new GeneralResult(enter.getRequestId());
    }


    @Async
    public void deleRosOrder(SiteOrder siteOrder) {
        QueryWrapper<SiteCustomer> qw = new QueryWrapper<>();
        qw.eq(SiteCustomer.COL_ID, siteOrder.getCustomerId());
        qw.last("limit 1");
        SiteCustomer customer = siteCustomerService.getOne(qw);
        if (customer == null) {
            throw new SesWebsiteException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(),
                    ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }
        siteWebInquiryService.webDeleteOrderAsynRos(customer.getEmail());

    }


    /**
     * 私有方法，计算当天后的多少天
     *
     * @param num
     * @return Date
     */
    private Date getDateForDay(int num) {
        SimpleDateFormat s = new SimpleDateFormat(DateConstant.DEFAULT_DATETIME_FORMAT);
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, num);
        return c.getTime();
    }
}
