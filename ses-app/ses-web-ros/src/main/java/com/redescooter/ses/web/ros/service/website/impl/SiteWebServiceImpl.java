package com.redescooter.ses.web.ros.service.website.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.enums.inquiry.InquirySourceEnums;
import com.redescooter.ses.api.common.enums.website.ProductColorEnums;
import com.redescooter.ses.api.common.enums.website.ProductModelEnums;
import com.redescooter.ses.api.common.service.SiteWebInquiryService;
import com.redescooter.ses.api.common.vo.inquiry.SiteWebInquiryEnter;
import com.redescooter.ses.api.common.vo.inquiry.SiteWebInquiryPayEnter;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.website.WebsiteInquiryServiceMapper;
import com.redescooter.ses.web.ros.dm.OpeCustomer;
import com.redescooter.ses.web.ros.dm.OpeCustomerInquiry;
import com.redescooter.ses.web.ros.dm.OpeCustomerInquiryB;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeCustomerInquiryBService;
import com.redescooter.ses.web.ros.service.base.OpeCustomerInquiryService;
import com.redescooter.ses.web.ros.service.base.OpeCustomerService;
import com.redescooter.ses.web.ros.service.monday.MondayService;
import com.redescooter.ses.web.ros.vo.monday.enter.MondayBookOrderEnter;
import com.redescooter.ses.web.ros.vo.monday.enter.MondayGeneralEnter;
import com.redescooter.ses.web.ros.vo.website.ProductResult;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
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
    private OpeCustomerInquiryBService opeCustomerInquiryBService;

    @Autowired
    private OpeCustomerService opeCustomerService;

    @Autowired
    private MondayService mondayService;

    @Autowired
    private WebsiteInquiryServiceMapper websiteInquiryServiceMapper;

    @DubboReference
    private IdAppService idAppService;

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
        if (customer == null) {
            throw new SesWebRosException(ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getCode(), ExceptionCodeEnums.CUSTOMER_NOT_EXIST.getMessage());
        }
        inquiry.setFirstName(customer.getCustomerFirstName());
        inquiry.setLastName(customer.getCustomerLastName());
        inquiry.setTelephone(customer.getTelephone());
        inquiry.setEmail(customer.getEmail());
        inquiry.setContactFirst(customer.getContactFirstName());
        inquiry.setContactLast(customer.getContactLastName());
        inquiry.setContantFullName(customer.getContactFullName());
        inquiry.setSource(InquirySourceEnums.ORDER_FORM.getValue());
        inquiry.setProductModel(enter.getProductModel());
        opeCustomerInquiryService.saveOrUpdate(inquiry);

        // 同步ROS的询价单的字表
        OpeCustomerInquiryB inquiryB = new OpeCustomerInquiryB();
        inquiryB.setId(idAppService.getId(SequenceName.OPE_CUSTOMER_INQUIRY_B));
        inquiryB.setInquiryId(inquiry.getId());
        inquiryB.setProductId(inquiry.getProductId());
        inquiryB.setProductPrice(inquiry.getProductPrice());
        // 这个不知道是啥
        // inquiryB.setProductType(inquiry.);
        inquiryB.setProductQty(1);
        inquiryB.setCreatedTime(new Date());
        inquiryB.setCreatedBy(0L);
        inquiryB.setUpdatedTime(new Date());
        inquiryB.setUpdatedBy(0L);
        opeCustomerInquiryBService.saveOrUpdate(inquiryB);

        ProductResult product = websiteInquiryServiceMapper.queryProductById(enter.getProductId());
        if (product == null) {
            throw new SesWebRosException(ExceptionCodeEnums.PART_PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PART_PRODUCT_IS_NOT_EXIST.getMessage());
        }
        //发送数据到Monday
        mondayData(product.getColor(), enter.getBatteryQty(), product.getProductModel(), inquiry);
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
        mondayBookOrderEnter.setProductColor(Objects.requireNonNull(ProductColorEnums.getProductColorEnumsByValue(productColor)).getMessage());
        mondayBookOrderEnter.setBatteryQty(batteryQty);
        mondayBookOrderEnter.setProducModeltName(Objects.requireNonNull(ProductModelEnums.getProductModelEnumsByValue(productModel)).getMessage());
        mondayBookOrderEnter.setQty(1);
        mondayGeneralEnter.setT(mondayBookOrderEnter);
        //Monday 同步数据
        mondayService.websiteBookOrder(mondayGeneralEnter);
    }


    /**
     * 官网订单支付之后调用的 不管支付成功还是失败  都要调用
     * @param enter
     */
    @Override
    public void siteWebInquiryPay(SiteWebInquiryPayEnter enter) {

        OpeCustomerInquiry inquiry = opeCustomerInquiryService.getById(enter.getId());
        if (inquiry == null) {
            throw new SesWebRosException(ExceptionCodeEnums.INQUIRY_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.INQUIRY_IS_NOT_EXIST.getMessage());
        }
        BeanUtils.copyProperties(enter, inquiry);
        opeCustomerInquiryService.updateById(inquiry);
    }


    @Override
    public void webDeleteOrderAsynRos(String email) {
        QueryWrapper<OpeCustomerInquiry> inquiry = new QueryWrapper<>();
        inquiry.eq(OpeCustomerInquiry.COL_EMAIL,email);
        List<OpeCustomerInquiry> inquiryList = opeCustomerInquiryService.list(inquiry);
        if (CollectionUtils.isNotEmpty(inquiryList)){
            // 找到ROS这边的订单  给删除调（讲道理 这个时候应该只有一个订单为了以后扩展 这里当作多个订单处理）
            opeCustomerInquiryService.removeByIds(inquiryList.stream().map(OpeCustomerInquiry::getId).collect(Collectors.toList()));
            // 再删除子订单
            QueryWrapper<OpeCustomerInquiryB> inquiryB = new QueryWrapper<>();
            inquiryB.in(OpeCustomerInquiryB.COL_INQUIRY_ID,inquiryList.stream().map(OpeCustomerInquiry::getId).collect(Collectors.toList()));
            List<OpeCustomerInquiryB> inquiryBS = opeCustomerInquiryBService.list(inquiryB);
            if (CollectionUtils.isNotEmpty(inquiryBS)){
                opeCustomerInquiryBService.removeByIds(inquiryBS.stream().map(OpeCustomerInquiryB::getId).collect(Collectors.toList()));
            }
        }
    }


}
