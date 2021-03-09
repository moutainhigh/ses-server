package com.redescooter.ses.web.website.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.constant.DateConstant;
import com.redescooter.ses.api.common.service.SiteWebInquiryService;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.IdResult;
import com.redescooter.ses.api.common.vo.inquiry.SiteWebInquiryEnter;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.web.website.constant.SequenceName;
import com.redescooter.ses.web.website.dao.OrderMapper;
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
import com.redescooter.ses.web.website.vo.order.AddPartListEnter;
import com.redescooter.ses.web.website.vo.order.AddUpdateOrderEnter;
import com.redescooter.ses.web.website.vo.order.OrderDetailsResult;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
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
    public IdResult addOrder(AddUpdateOrderEnter enter) {
        SiteOrder addSiteOrderVO = null;
        //获取当前登录用户
        SiteUser user = siteUserService.getById(enter.getUserId());

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
            String orderNo = new StringBuffer().append("RED").append(format).append(String.valueOf(idAppService.getId(SequenceName.SITE_ORDER)).substring(5)).toString();
            addSiteOrderVO.setOrderNo(orderNo);
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
            //先删除订单下的主订单,为了方便下步创建新的配件列表
            siteOrderBService.remove(new QueryWrapper<SiteOrderB>().eq(SiteOrderB.COL_ORDER_ID, addSiteOrderVO.getId()));
        }

        if (user == null || enter.getColourId() == null || enter.getProductId() == null) {
            throw new SesWebsiteException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(),
                    ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }

        SitePaymentType paymentType = sitePaymentTypeService.getById(enter.getPaymentTypeId());
        if (paymentType == null) {
            throw new SesWebsiteException(ExceptionCodeEnums.PAYMENTTYPE_NOT_EXIST_EXIST.getCode(),
                    ExceptionCodeEnums.PAYMENTTYPE_NOT_EXIST_EXIST.getMessage());
        }
        //获取产品
        SiteProduct product = siteProductService.getById(enter.getProductId());
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
        SiteProductPrice productPrice = siteProductPriceService.getOne(new QueryWrapper<SiteProductPrice>()
                .eq(SiteProductPrice.COL_DR, Constant.DR_FALSE)
                .eq(SiteProductPrice.COL_PRODUCT_MODEL_ID, product.getProductModelId()));
        if (productPrice == null) {
            throw new SesWebsiteException(ExceptionCodeEnums.PARAM_ERROR.getCode(),
                    ExceptionCodeEnums.PARAM_ERROR.getMessage());
        }

        //获取所选车辆电池数量
        SiteProductParts scooterBatteryParts = siteProductPartsService.getById(enter.getProductPartsId());
        if (scooterBatteryParts == null) {
            throw new SesWebsiteException(ExceptionCodeEnums.BATTERIES_NUM_ERROR.getCode(),
                    ExceptionCodeEnums.BATTERIES_NUM_ERROR.getMessage());
        }
        //获取电池
        SiteParts battery = sitePartsService.getById(scooterBatteryParts.getPartsId());
        if (battery == null) {
            throw new SesWebsiteException(ExceptionCodeEnums.PARTS_NOT_EXIST_EXIST.getCode(),
                    ExceptionCodeEnums.PARTS_NOT_EXIST_EXIST.getMessage());
        }

        addSiteOrderVO.setOrderType(SiteOrderTypeEnums.checkValue(enter.getOrderType()));
        addSiteOrderVO.setDeliveryType(DeliveryMethodEnums.checkValue(enter.getDeliveryType()));
        addSiteOrderVO.setProductId(productColour.getProductId());
        addSiteOrderVO.setBatteryQty(scooterBatteryParts.getQty());
        addSiteOrderVO.setColourId(productColour.getColourId());
        addSiteOrderVO.setFullName(customer.getCustomerFullName());
        addSiteOrderVO.setCountryName(customer.getCountryName());
        addSiteOrderVO.setCityName(customer.getCityName());
        addSiteOrderVO.setPostcode(customer.getPostcode());
        //这里是送货地址
        addSiteOrderVO.setAddress(enter.getDeliveryAddress());
        //运费
        addSiteOrderVO.setFreight(new BigDecimal("190"));
        //整车价格
        addSiteOrderVO.setProductPrice(productPrice.getPrice());
        //没有选着其他配件前，只是整车的价格+运费
        BigDecimal totalPrice = new BigDecimal("190").add(productPrice.getPrice().multiply(new BigDecimal(enter.getScooterQuantity())));
        //实际付款电池数: 选配电池总数-产品最小电池数
        int paidBattery = scooterBatteryParts.getQty() - product.getMinBatteryNum();
        //加上选购电池的价格
        totalPrice = totalPrice.add(battery.getPrice().multiply(new BigDecimal(String.valueOf(paidBattery))));
        //设置总价（未包含其他部件）
        addSiteOrderVO.setTotalPrice(totalPrice);
        //已付金额
        addSiteOrderVO.setAmountPaid(new BigDecimal("0"));
        //代付款金额
        addSiteOrderVO.setAmountObligation(addSiteOrderVO.getTotalPrice());
        //预付定金
        addSiteOrderVO.setPrepaidDeposit(new BigDecimal("590"));
        //优惠抵扣金额
        addSiteOrderVO.setAmountDiscount(new BigDecimal("0"));
        //支付类型
        addSiteOrderVO.setPaymentTypeId(enter.getPaymentTypeId());
        //支付状态
        addSiteOrderVO.setPayStatus(SiteOrderPaymentStatusEnums.UN_PAID.getValue());
        //购买电池数
        addSiteOrderVO.setBatteryQty(scooterBatteryParts.getQty());
        //购买车辆数
        addSiteOrderVO.setScooterQuantity(enter.getScooterQuantity());
        addSiteOrderVO.setSynchronizeFlag(false);
        addSiteOrderVO.setEtdDeliveryTime(this.getDateForDay(45));
        addSiteOrderVO.setRevision(0);
        addSiteOrderVO.setCreatedBy(enter.getUserId());
        addSiteOrderVO.setCreatedTime(new Date());
        addSiteOrderVO.setUpdatedBy(enter.getUserId());

        siteOrderService.saveOrUpdate(addSiteOrderVO);
        IdResult result = new IdResult();
        result.setId(addSiteOrderVO.getId());
        result.setRequestId(enter.getRequestId());
        try {
            // 官网的订单数据 要同步到ROS系统中
            syncdataToRos(addSiteOrderVO);
        } catch (Exception e) {

        }
        return result;
    }

    /**
     * 这个方法要使用异步请求
     *
     * @param addSiteOrderVO
     */
    @Async
    void syncdataToRos(SiteOrder addSiteOrderVO) {
        // 构造请求的参数
        SiteWebInquiryEnter enter = new SiteWebInquiryEnter();
        BeanUtils.copyProperties(addSiteOrderVO, enter);
        // 給productModel赋值
        enter.setProductModel(orderMapper.getProductModelByOrderId(addSiteOrderVO.getId()));
        // 调方法 同步数据
        siteWebInquiryService.siteWebOrderToRosInquiry(enter);

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

        Long orderId = enter.getOrderId();
        if (orderId == 0) {
            throw new SesWebsiteException(ExceptionCodeEnums.ORDER_NOT_EXIST_EXIST.getCode(),
                    ExceptionCodeEnums.ORDER_NOT_EXIST_EXIST.getMessage());
        }
        //获取订单
        SiteOrder order = siteOrderService.getById(orderId);
        //获取配件
        List<AddPartListEnter> partslist = enter.getPartslist();
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
            //单个配件价格合
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
        BigDecimal orderTotalPrice = order.getTotalPrice().add(partAllTotalPrice);
        //更新总金额
        order.setTotalPrice(orderTotalPrice);
        //更新待付金额
        order.setAmountObligation(orderTotalPrice);
        //所选择配件总价格做备份记录
        order.setDef1(partAllTotalPrice.toPlainString());

        //更新主订单金额
        siteOrderService.updateById(order);
        //创建子订单
        siteOrderBService.batchInsert(list);
        return new GeneralResult(enter.getRequestId());
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
