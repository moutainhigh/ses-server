package com.redescooter.ses.web.ros.service.restproductionorder.purchas.impl;

import com.redescooter.ses.api.common.enums.restproductionorder.productionpurchas.ProductionPurchasEnums;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.service.restproductionorder.purchas.ProductionPurchasService;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchass.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName:ProductionPurchasServiceImpl
 * @description: ProductionPurchasServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2020/11/11 11:43 
 */
@Service
@Slf4j
public class ProductionPurchasServiceImpl implements ProductionPurchasService {

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/10 2:52 下午
     * @Param: enter
     * @Return: ProductionPurchasListResult
     * @desc: 列表
     * @param enter
     */
    @Override
    public PageResult<ProductionPurchasListResult> list(ProductionPurchasListEnter enter) {
        List<ProductionPurchasListResult> result = new ArrayList<>();
        ProductionPurchasListResult productionPurchasListResult = new ProductionPurchasListResult();
        productionPurchasListResult.setId(3211231L);
        productionPurchasListResult.setPurchaseNo("dasddadasdasda");
        productionPurchasListResult.setPurchaseStatus(ProductionPurchasEnums.CANCEL.getValue());
        productionPurchasListResult.setPurchaseQty(1);
        productionPurchasListResult.setDeliveryDate(new Date());
        productionPurchasListResult.setPurchaseAmount("200");
        productionPurchasListResult.setFactoryId(4234234L);
        productionPurchasListResult.setFactoryName("你才");
        productionPurchasListResult.setDockingUser(3213131L);
        productionPurchasListResult.setDockingUserName("ddadaad");
        productionPurchasListResult.setDockingCountryCode("33");
        productionPurchasListResult.setDockingUserTelephone("432434323242");
        productionPurchasListResult.setCreateById(32242L);
        productionPurchasListResult.setCreateByFirstName("244244");
        productionPurchasListResult.setCreateByLastName("2324234");
        productionPurchasListResult.setCreateTime(new Date());
        result.add(productionPurchasListResult);
        return PageResult.create(enter,1,result);
    }

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/10 3:06 下午
     * @Param: enter
     * @Return: ProductionPurchasDetailResult
     * @desc: 详情
     * @param enter
     */
    @Override
    public ProductionPurchasDetailResult detail(IdEnter enter) {
        ProductionPurchasDetailResult productionPurchasDetailResult = new ProductionPurchasDetailResult();
        productionPurchasDetailResult.setId(3211231L);
        productionPurchasDetailResult.setPurchaseNo("dasddadasdasda");
        productionPurchasDetailResult.setPurchaseStatus(ProductionPurchasEnums.CANCEL.getValue());
        productionPurchasDetailResult.setDockingUser(14234324L);
        productionPurchasDetailResult.setDockingUserName("eqeqeqeq");
        productionPurchasDetailResult.setDockingCountryCode("33");
        productionPurchasDetailResult.setDockingUserTelephone("4234234L");
        productionPurchasDetailResult.setFactoryId(42422L);
        productionPurchasDetailResult.setFactoryName("你才");
        productionPurchasDetailResult.setFactoryId(3213131L);
        productionPurchasDetailResult.setFactoryName("ddadaad");
        productionPurchasDetailResult.setPrincipalCountryCode("33");
        productionPurchasDetailResult.setPrincipalTelephone("432434323242");
        productionPurchasDetailResult.setConsigneeUser(32242L);
        productionPurchasDetailResult.setConsigneeUserName("244244");
        productionPurchasDetailResult.setConsigneeCountryCode("33");
        productionPurchasDetailResult.setConsigneeUserTelephone("42343242");
        productionPurchasDetailResult.setConsigneeAddress("eqewqeq");
        productionPurchasDetailResult.setRemark("42343242423");
        return productionPurchasDetailResult;
    }

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/10 4:08 下午
     * @Param: enter
     * @Return: GeneralResult
     * @desc: 保存采购单
     * @param enter
     */
    @Transactional
    @Override
    public GeneralResult save(SaveProductionPurchasEnter enter) {
        return null;
    }
}
