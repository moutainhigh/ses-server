package com.redescooter.ses.web.ros.service.website.impl;

import com.redescooter.ses.api.common.enums.customer.CustomerIndustryEnums;
import com.redescooter.ses.api.common.enums.customer.CustomerSourceEnum;
import com.redescooter.ses.api.common.enums.customer.CustomerTypeEnum;
import com.redescooter.ses.api.common.enums.inquiry.InquiryStatusEnums;
import com.redescooter.ses.api.common.enums.website.AccessoryTypeEnums;
import com.redescooter.ses.api.common.enums.website.ProductColorEnums;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.website.WebsiteInquiryServiceMapper;
import com.redescooter.ses.web.ros.dm.OpeCustomerAccessories;
import com.redescooter.ses.web.ros.dm.OpeCustomerInquiry;
import com.redescooter.ses.web.ros.dm.OpeCustomerInquiryB;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeCustomerInquiryBService;
import com.redescooter.ses.web.ros.service.base.OpeCustomerInquiryService;
import com.redescooter.ses.web.ros.service.base.OpePartsProductService;
import com.redescooter.ses.web.ros.service.base.impl.OpeCustomerAccessoriesService;
import com.redescooter.ses.web.ros.service.website.WebsiteOrderFormService;
import com.redescooter.ses.web.ros.vo.website.AccessoryResult;
import com.redescooter.ses.web.ros.vo.website.ProductColorResult;
import com.redescooter.ses.web.ros.vo.website.ProductResult;
import com.redescooter.ses.web.ros.vo.website.SaveSaleOrderEnter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Reference
    private IdAppService idAppService;

    /**
     * 车辆型号
     *
     * @param enter
     * @return
     */
    @Override
    public List<ProductResult> scooterList(GeneralEnter enter) {
        return websiteInquiryServiceMapper.scooterList(enter);
    }

    /**
     * 车辆颜色
     *
     * @param enter
     * @return
     */
    @Override
    public List<ProductColorResult> scooterColors(GeneralEnter enter) {
        List<ProductColorResult> resultList = new ArrayList<>();
        for (ProductColorEnums item : ProductColorEnums.values()) {
            resultList.add(ProductColorResult.builder()
                    .id(Long.valueOf(item.getValue()))
                    .color(item.getCode())
                    .build());
        }
        return resultList;
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
    public GeneralResult saveOrderForm(SaveSaleOrderEnter enter) {
        OpeCustomerAccessories topCase = opeCustomerAccessoriesService.getById(enter.getTopCaseId());
        if (topCase == null) {
            throw new SesWebRosException(ExceptionCodeEnums.TOP_CASE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.TOP_CASE_IS_NOT_EXIST.getMessage());
        }

        OpeCustomerAccessories battery = opeCustomerAccessoriesService.getById(enter.getAccessoryBatteryId());
        if (battery == null) {
            throw new SesWebRosException(ExceptionCodeEnums.BATTERY_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.BATTERY_IS_NOT_EXIST.getMessage());
        }

        ProductResult product = websiteInquiryServiceMapper.queryProductById(enter.getProductId());
        if (product == null) {
            throw new SesWebRosException(ExceptionCodeEnums.PART_PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PART_PRODUCT_IS_NOT_EXIST.getMessage());
        }


        BigDecimal totalPrice = product.getPrice().add(battery.getPrice().multiply(new BigDecimal(enter.getAccessoryBatteryQty())));

        //电池、后备箱
        if (enter.getTopCaseId() != 0 && enter.getTopCaseId() != null) {
            totalPrice = product.getPrice().add(battery.getPrice().multiply(new BigDecimal(enter.getAccessoryBatteryQty()))).add(topCase.getPrice());
        }

        //配件保存集合
        List<OpeCustomerInquiryB> opeCustomerInquiryBList = new ArrayList<>();
        //生成主订单
        OpeCustomerInquiry opeCustomerInquiry = new OpeCustomerInquiry();
        opeCustomerInquiry.setId(idAppService.getId(SequenceName.OPE_CUSTOMER_INQUIRY));
        opeCustomerInquiry.setDr(0);
        opeCustomerInquiry.setCustomerSource(CustomerSourceEnum.WEBSITE.getValue());
        opeCustomerInquiry.setStatus(InquiryStatusEnums.UNPROCESSED.getValue());
        //默认 客户类型 为个人、行业为 餐厅
        opeCustomerInquiry.setIndustry(CustomerIndustryEnums.RESTAURANT.getValue());
        opeCustomerInquiry.setCustomerType(CustomerTypeEnum.PERSONAL.getValue());

        opeCustomerInquiry.setProductId(enter.getProductId());
        opeCustomerInquiry.setProductPrice(product.getPrice());
        opeCustomerInquiry.setTotalPrice(totalPrice);

        opeCustomerInquiry.setScooterQuantity(enter.getProductQty());
        opeCustomerInquiry.setCreatedBy(enter.getUserId());
        opeCustomerInquiry.setCreatedTime(new Date());
        opeCustomerInquiry.setUpdatedBy(enter.getUserId());
        opeCustomerInquiry.setUpdatedTime(new Date());
        opeCustomerInquiryService.save(opeCustomerInquiry);

        //生成子订单

        if (enter.getTopCaseId() != 0 && enter.getTopCaseId() != null) {
            //后备箱形成子表记录
            opeCustomerInquiryBList.add(buildAccessory(enter, opeCustomerInquiry.getId(), topCase.getPrice(), AccessoryTypeEnums.TOP_CASE.getValue()));
        }
        //电池记录
        opeCustomerInquiryBList.add(buildAccessory(enter, opeCustomerInquiry.getId(), battery.getPrice(), AccessoryTypeEnums.BATTERY.getValue()));

        //子表数据
        if (CollectionUtils.isNotEmpty(opeCustomerInquiryBList)) {
            opeCustomerInquiryBService.saveBatch(opeCustomerInquiryBList);
        }
        return new GeneralResult(enter.getRequestId());
    }

    private OpeCustomerInquiryB buildAccessory(SaveSaleOrderEnter enter, Long id, BigDecimal price, String type) {
        OpeCustomerInquiryB opeCustomerInquiryB = new OpeCustomerInquiryB();
        opeCustomerInquiryB.setId(idAppService.getId(SequenceName.OPE_CUSTOMER_INQUIRY_B));
        opeCustomerInquiryB.setDr(0);
        opeCustomerInquiryB.setInquiryId(id);
        opeCustomerInquiryB.setAccessoryId(StringUtils.equals(type, AccessoryTypeEnums.TOP_CASE.getValue()) == true ? enter.getTopCaseId() : enter.getAccessoryBatteryId());
        opeCustomerInquiryB.setAccessoryPrice(price);
        opeCustomerInquiryB.setAccessoryQty(StringUtils.equals(type, AccessoryTypeEnums.TOP_CASE.getValue()) == true ? 1 : enter.getAccessoryBatteryQty());
        opeCustomerInquiryB.setAccessoryType(type);
        opeCustomerInquiryB.setProductId(enter.getProductId());
        opeCustomerInquiryB.setCreatedBy(enter.getUserId());
        opeCustomerInquiryB.setCreatedTime(new Date());
        opeCustomerInquiryB.setUpdatedBy(enter.getUserId());
        opeCustomerInquiryB.setUpdatedTime(new Date());
        return opeCustomerInquiryB;
    }
}
