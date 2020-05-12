package com.redescooter.ses.web.ros.service.website.impl;

import com.redescooter.ses.api.common.enums.website.AccessoryBatteryEnums;
import com.redescooter.ses.api.common.enums.website.ProductColorEnums;
import com.redescooter.ses.api.common.enums.website.ProductTypeEnums;
import com.redescooter.ses.api.common.enums.website.TopClassEnums;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.web.ros.service.base.OpeCustomerInquiryService;
import com.redescooter.ses.web.ros.service.website.WebsiteInquiryService;
import com.redescooter.ses.web.ros.vo.website.AccessoryBatteryResult;
import com.redescooter.ses.web.ros.vo.website.ProductColorResult;
import com.redescooter.ses.web.ros.vo.website.ProductTypeResult;
import com.redescooter.ses.web.ros.vo.website.SaveSaleOrderEnter;
import com.redescooter.ses.web.ros.vo.website.TopClassResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
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
public class WebsiteInquiryServiceImpl implements WebsiteInquiryService {

    @Autowired
    private OpeCustomerInquiryService opeCustomerInquiryService;

    @Reference
    private IdAppService idAppService;

    /**
     * 车辆型号
     *
     * @param enter
     * @return
     */
    @Override
    public List<ProductTypeResult> scooterType(GeneralEnter enter) {
        List<ProductTypeResult> resultList=new ArrayList<>();
        for (ProductTypeEnums item : ProductTypeEnums.values()) {
            resultList.add(ProductTypeResult
                    .builder()
                    .id(Long.valueOf(item.getValue()))
                    .productType(item.getCode())
                    .price(new BigDecimal(item.getPrice()))
                    .build());
        }
        return resultList;
    }

    /**
     * 车辆颜色
     *
     * @param enter
     * @return
     */
    @Override
    public List<ProductColorResult> scooterColor(GeneralEnter enter) {
        List<ProductColorResult> resultList=new ArrayList<>();
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
    public List<AccessoryBatteryResult> accessoryBatteryList(GeneralEnter enter) {
        List<AccessoryBatteryResult> resultList=new ArrayList<>();
        for (AccessoryBatteryEnums item : AccessoryBatteryEnums.values()) {
            resultList.add(AccessoryBatteryResult.builder()
                    .id(Long.valueOf(item.getValue()))
                    .accessoryBatteryType(item.getCode())
                    .accessoryBatteryName(item.getCode())
                    .price(new BigDecimal(item.getPrice()))
                    .build());
        }

        return resultList;
    }


    /**
     * 后备箱
     *
     * @param enter
     * @return
     */
    @Override
    public List<TopClassResult> topClass(GeneralEnter enter) {
        List<TopClassResult> resultList=new ArrayList<>();
        for (TopClassEnums item : TopClassEnums.values()) {
            resultList.add(TopClassResult
                    .builder()
                    .id(Long.valueOf(item.getValue()))
                    .name(item.getCode())
                    .price(new BigDecimal(item.getPrice()))
                    .build());
        }
        return resultList;
    }

    /**
     * 保存 询价单
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult saveInquiry(SaveSaleOrderEnter enter) {

        //生成主订单
//        OpeCustomerInquiry opeCustomerInquiry = new OpeCustomerInquiry();
//        opeCustomerInquiry.setId(idAppService.getId(SequenceName.OPE_CUSTOMER_INQUIRY));
//        opeCustomerInquiry.setDr(0);
//        opeCustomerInquiry.setCustomerSource(CustomerSourceEnum.WEBSITE.getValue());
//        opeCustomerInquiry.setStatus(InquiryStatusEnums.UNPROCESSED.getValue());
//        //默认
//        opeCustomerInquiry.setIndustry(CustomerIndustryEnums.RESTAURANT.getValue());
//        opeCustomerInquiry.setCustomerType(CustomerTypeEnum.PERSONAL.getValue());
//
//        opeCustomerInquiry.setProductId(enter.getProductTypeId());
//        opeCustomerInquiry.setScooterQuantity(enter.getProductQty());
//        opeCustomerInquiry.setCreatedBy(enter.getUserId());
//        opeCustomerInquiry.setCreatedTime(new Date());
//        opeCustomerInquiry.setUpdatedBy(enter.getUserId());
//        opeCustomerInquiry.setUpdatedTime(new Date());
//        opeCustomerInquiryService.save(opeCustomerInquiry);


        //生成子订单


        return null;
    }
}
