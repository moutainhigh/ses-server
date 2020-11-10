package com.redescooter.ses.web.ros.service.restproductionorder.purchas;

import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchass.ProductionPurchasDetailResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchass.ProductionPurchasListEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchass.ProductionPurchasListResult;

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
}
