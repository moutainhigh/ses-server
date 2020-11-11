package com.redescooter.ses.web.ros.service.restproductionorder.purchas;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.SupplierListResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchass.ProductionPurchasDetailResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchass.ProductionPurchasListEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchass.ProductionPurchasListResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchass.SaveProductionPurchasEnter;

import java.util.List;

/**
 * @ClassName:ProductionPurchasService
 * @description: ProductionPurchasService
 * @author: Alex
 * @Version：1.3
 * @create: 2020/11/10 14:39 
 */
public interface ProductionPurchasService {
    /**
    * @Description
    * @Author: alex
    * @Date:   2020/11/10 2:52 下午
    * @Param:  enter
    * @Return: ProductionPurchasListResult
    * @desc: 列表
    */
   PageResult<ProductionPurchasListResult> list(ProductionPurchasListEnter enter);
    /**
    * @Description
    * @Author: alex
    * @Date:   2020/11/10 3:06 下午
    * @Param:  enter
    * @Return: ProductionPurchasDetailResult
    * @desc: 详情
    */
    ProductionPurchasDetailResult detail(IdEnter enter);
    /**
    * @Description
    * @Author: alex
    * @Date:   2020/11/10 4:08 下午
    * @Param:  enter
    * @Return: GeneralResult
    * @desc: 保存采购单
    */
    GeneralResult save(SaveProductionPurchasEnter enter);
    /**
    * @Description
    * @Author: alex
    * @Date:   2020/11/11 3:21 下午
    * @Param:  enter
    * @Return: SupplierListResult
    * @desc: 供应商列表
    */
    List<SupplierListResult>  supplierList(GeneralEnter enter);
}
