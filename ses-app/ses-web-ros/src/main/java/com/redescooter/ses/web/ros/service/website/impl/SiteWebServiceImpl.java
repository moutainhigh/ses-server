package com.redescooter.ses.web.ros.service.website.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.enums.customer.CustomerSourceEnum;
import com.redescooter.ses.api.common.enums.inquiry.InquirySourceEnums;
import com.redescooter.ses.api.common.enums.website.ProductColorEnums;
import com.redescooter.ses.api.common.service.SiteWebInquiryService;
import com.redescooter.ses.api.common.vo.inquiry.SiteWebInquiryEnter;
import com.redescooter.ses.api.common.vo.inquiry.SiteWebInquiryPayEnter;
import com.redescooter.ses.api.common.vo.inquiry.SiteWebInquiryPriceEnter;
import com.redescooter.ses.api.hub.service.website.ProductionService;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.constant.StringManaConstant;
import com.redescooter.ses.web.ros.dao.base.OpeCustomerInquiryMapper;
import com.redescooter.ses.web.ros.dao.delete.DeleteMapper;
import com.redescooter.ses.web.ros.dao.website.WebsiteInquiryServiceMapper;
import com.redescooter.ses.web.ros.dm.OpeColor;
import com.redescooter.ses.web.ros.dm.OpeCustomer;
import com.redescooter.ses.web.ros.dm.OpeCustomerInquiry;
import com.redescooter.ses.web.ros.dm.OpeCustomerInquiryB;
import com.redescooter.ses.web.ros.dm.OpeSaleScooter;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeColorService;
import com.redescooter.ses.web.ros.service.base.OpeCustomerInquiryBService;
import com.redescooter.ses.web.ros.service.base.OpeCustomerInquiryService;
import com.redescooter.ses.web.ros.service.base.OpeCustomerService;
import com.redescooter.ses.web.ros.service.base.OpeSaleScooterService;
import com.redescooter.ses.web.ros.service.monday.MondayService;
import com.redescooter.ses.web.ros.vo.monday.enter.MondayBookOrderEnter;
import com.redescooter.ses.web.ros.vo.monday.enter.MondayGeneralEnter;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: Aleks
 * @create: 2021/01/27 16:02
 */
@DubboService
@Slf4j
public class SiteWebServiceImpl implements SiteWebInquiryService {

    @Autowired
    private OpeCustomerInquiryService opeCustomerInquiryService;

    @Autowired
    private OpeCustomerInquiryMapper opeCustomerInquiryMapper;

    @Autowired
    private OpeSaleScooterService opeSaleScooterService;

    @Autowired
    private OpeColorService opeColorService;

    @Autowired
    private OpeCustomerInquiryBService opeCustomerInquiryBService;

    @Autowired
    private OpeCustomerService opeCustomerService;

    @Autowired
    private DeleteMapper deleteMapper;

    @Autowired
    private WebsiteInquiryServiceMapper websiteInquiryServiceMapper;

    @DubboReference
    private IdAppService idAppService;

    @DubboReference
    private ProductionService productionService;

    /**
     * 官网的订单数据同步到ROS中
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public void siteWebOrderToRosInquiry(SiteWebInquiryEnter enter, String email) {
        log.info("开始进入ros进行同步");
        OpeCustomerInquiry inquiry = new OpeCustomerInquiry();
        BeanUtils.copyProperties(enter, inquiry);
        inquiry.setCreatedBy(0L);
        inquiry.setUpdatedBy(0L);
        inquiry.setCreatedTime(new Date());
        inquiry.setUpdatedTime(new Date());
        // 等价城市
        inquiry.setDistrict(enter.getCityName());
        inquiry.setCountry(enter.getCountryName());
        inquiry.setDef1(enter.getCountryName());
        inquiry.setCountryCode("33");
        inquiry.setCity(enter.getCityName());
        inquiry.setDef2(enter.getCityName());
        inquiry.setPostCode(enter.getPostcode());
        inquiry.setDef3(enter.getPostcode());
        inquiry.setBankCardName(enter.getBankCardName());
        // 1快递 2餐厅 3自由行业
        inquiry.setIndustry("3");
        // 2表示个人
        inquiry.setCustomerType("2");
        // 下面的数据是从客户表来的
        LambdaQueryWrapper<OpeCustomer> qw = new LambdaQueryWrapper<>();
        qw.eq(OpeCustomer::getDr, Constant.DR_FALSE);
        qw.eq(OpeCustomer::getEmail, email);
        qw.last("limit 1");
        OpeCustomer customer = opeCustomerService.getOne(qw);
        if (StringManaConstant.entityIsNull(customer)) {
            throw new SesWebRosException(ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getCode(), ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getMessage());
        }
        inquiry.setCustomerId(customer.getId());
        inquiry.setCustomerSource(CustomerSourceEnum.WEBSITE.getValue());
        inquiry.setStatus(String.valueOf(enter.getStatus()));
        inquiry.setProductModel("1");
        inquiry.setPayStatus(String.valueOf(enter.getPayStatus()));
        inquiry.setFirstName(customer.getCustomerFirstName());
        inquiry.setLastName(customer.getCustomerLastName());
        inquiry.setTelephone(customer.getTelephone());
        inquiry.setEmail(customer.getEmail());
        inquiry.setContactFirst(customer.getContactFirstName());
        inquiry.setContactLast(customer.getContactLastName());
        inquiry.setContantFullName(customer.getContactFullName());
        inquiry.setSource(InquirySourceEnums.ORDER_FORM.getValue());
        inquiry.setProductModel(enter.getProductModel());
        inquiry.setAmountPaid(enter.getAmountPaid());
        inquiry.setAmountObligation(enter.getAmountObligation());
        inquiry.setTotalPrice(enter.getTotalPrice());

        // 将productId转为销售车辆id,根据名字,颜色,型号,开启状态进行匹配
        Long productId = inquiry.getProductId();
        if (StringManaConstant.entityIsNotNull(productId)) {
            Map<String, String> map = productionService.getProductInfoByModelId(productId);
            if (StringManaConstant.entityIsNotNull(map)) {
                String colorCode = map.get("colorCode");
                String code = map.get("code");
                String name = map.get("name");

                // 根据colorName获得colorId
                LambdaQueryWrapper<OpeColor> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(OpeColor::getDr, Constant.DR_FALSE);
                wrapper.eq(OpeColor::getColorValue, colorCode);
                wrapper.last("limit 1");
                OpeColor color = opeColorService.getOne(wrapper);
                if (StringManaConstant.entityIsNull(color)) {
                    throw new SesWebRosException(ExceptionCodeEnums.COLOR_NOT_EXIST.getCode(), ExceptionCodeEnums.COLOR_NOT_EXIST.getMessage());
                }
                Long colorId = color.getId();

                // 匹配
                LambdaQueryWrapper<OpeSaleScooter> lqw = new LambdaQueryWrapper<>();
                lqw.eq(OpeSaleScooter::getDr, Constant.DR_FALSE);
                lqw.eq(OpeSaleScooter::getColorId, colorId);
                lqw.eq(OpeSaleScooter::getSaleStutas, 1);
                lqw.eq(OpeSaleScooter::getProductName, name);
                lqw.eq(OpeSaleScooter::getProductCode, code);
                lqw.last("limit 1");
                OpeSaleScooter saleScooter = opeSaleScooterService.getOne(lqw);
                if (StringManaConstant.entityIsNotNull(saleScooter)) {
                    inquiry.setProductId(saleScooter.getId());
                }
            }
        }
        opeCustomerInquiryService.saveOrUpdate(inquiry);

        // 同步ROS的询价单的字表
        OpeCustomerInquiryB inquiryB = new OpeCustomerInquiryB();
        inquiryB.setId(idAppService.getId(SequenceName.OPE_CUSTOMER_INQUIRY_B));
        inquiryB.setInquiryId(inquiry.getId());
        inquiryB.setProductId(inquiry.getProductId());
        inquiryB.setProductPrice(inquiry.getProductPrice());
        // 这个不知道是啥
        // inquiryB.setProductType(inquiry.);
        inquiryB.setProductQty(enter.getBatteryQty() );
        inquiryB.setCreatedTime(new Date());
        inquiryB.setCreatedBy(0L);
        inquiryB.setUpdatedTime(new Date());
        inquiryB.setUpdatedBy(0L);
        opeCustomerInquiryBService.saveOrUpdate(inquiryB);

        /*ProductResult product = websiteInquiryServiceMapper.queryProductById(enter.getProductId());
        if (product == null) {
            throw new SesWebRosException(ExceptionCodeEnums.PART_PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PART_PRODUCT_IS_NOT_EXIST.getMessage());
        }
        //发送数据到Monday
        mondayData(product.getColor(), enter.getBatteryQty(), product.getProductModel(), inquiry);*/
        //mondayData("5", enter.getBatteryQty(), enter.getProductModel(), inquiry);
    }

    /**
     * 修改ROS预订单的金额
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public void updateRosInquiryPrice(SiteWebInquiryPriceEnter enter, String email) {
        LambdaQueryWrapper<OpeCustomer> qw = new LambdaQueryWrapper<>();
        qw.eq(OpeCustomer::getDr, Constant.DR_FALSE);
        qw.eq(OpeCustomer::getEmail, email);
        qw.last("limit 1");
        OpeCustomer customer = opeCustomerService.getOne(qw);
        if (StringManaConstant.entityIsNull(customer)) {
            throw new SesWebRosException(ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getCode(), ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getMessage());
        }

        // 要修改的字段
        OpeCustomerInquiry inquiry = new OpeCustomerInquiry();
        inquiry.setTotalPrice(enter.getTotalPrice());
        inquiry.setAmountObligation(enter.getAmountObligation());

        // 修改条件
        LambdaQueryWrapper<OpeCustomerInquiry> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OpeCustomerInquiry::getDr, Constant.DR_FALSE);
        wrapper.eq(OpeCustomerInquiry::getCustomerId, customer.getId());
        wrapper.eq(OpeCustomerInquiry::getCustomerSource, CustomerSourceEnum.WEBSITE.getValue());
        wrapper.eq(OpeCustomerInquiry::getTelephone, customer.getTelephone());
        wrapper.eq(OpeCustomerInquiry::getEmail, customer.getEmail());
        wrapper.eq(OpeCustomerInquiry::getSource, InquirySourceEnums.ORDER_FORM.getValue());
        opeCustomerInquiryService.update(inquiry, wrapper);
    }

    /**
     * 发送数据到Monday
     *
     * @param productModel
     * @param opeCustomerInquiry
     */
    private void mondayData(String productColor, int batteryQty, String productModel, OpeCustomerInquiry opeCustomerInquiry) {
        /*MondayGeneralEnter mondayGeneralEnter = new MondayGeneralEnter();
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
        mondayBookOrderEnter.setProductColor(Objects.requireNonNull(ProductColorEnums.getProductColorEnumsByValue(productColor)).getMessage());
        mondayBookOrderEnter.setBatteryQty(batteryQty);
        //mondayBookOrderEnter.setProducModeltName(Objects.requireNonNull(ProductModelEnums.getProductModelEnumsByValue(productModel)).getMessage());
        mondayBookOrderEnter.setProducModeltName(productModel);
        mondayBookOrderEnter.setQty(1);
        mondayGeneralEnter.setT(mondayBookOrderEnter);
        //Monday 同步数据
        mondayService.websiteBookOrder(mondayGeneralEnter);*/
    }


    /**
     * 官网订单支付之后调用的 不管支付成功还是失败  都要调用
     * @param enter
     */
    @Override
    public void siteWebInquiryPay(SiteWebInquiryPayEnter enter) {

        OpeCustomerInquiry inquiry = opeCustomerInquiryService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(inquiry)) {
            throw new SesWebRosException(ExceptionCodeEnums.INQUIRY_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.INQUIRY_IS_NOT_EXIST.getMessage());
        }
        BeanUtils.copyProperties(enter, inquiry);
        opeCustomerInquiryService.updateById(inquiry);
    }


    @Override
    public void webDeleteOrderAsynRos(String email) {
        QueryWrapper<OpeCustomerInquiry> inquiry = new QueryWrapper<>();
        inquiry.eq(OpeCustomerInquiry.COL_EMAIL,email);
        inquiry.eq(OpeCustomerInquiry.COL_DR,Constant.DR_FALSE);
        OpeCustomerInquiry inquiryList = opeCustomerInquiryMapper.selectOne(inquiry);
        if (null != inquiryList ){
            // 找到ROS这边的订单  给删除调（讲道理 这个时候应该只有一个订单为了以后扩展 这里当作多个订单处理）
            deleteMapper.deleteOrder(inquiryList.getId());
            // 再删除子订单
            QueryWrapper<OpeCustomerInquiryB> inquiryB = new QueryWrapper<>();
            inquiryB.eq(OpeCustomerInquiryB.COL_INQUIRY_ID,inquiryList.getId());
            List<OpeCustomerInquiryB> inquiryBS = opeCustomerInquiryBService.list(inquiryB);
            if (CollectionUtils.isNotEmpty(inquiryBS)){
                deleteMapper.deleteOrderB(inquiryList.getId());
            }
        }
    }


}
