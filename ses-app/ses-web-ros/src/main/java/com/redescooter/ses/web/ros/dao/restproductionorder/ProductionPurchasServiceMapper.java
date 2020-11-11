package com.redescooter.ses.web.ros.dao.restproductionorder;

import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchass.ProductionPurchasDetailResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchass.ProductionPurchasListEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchass.ProductionPurchasListResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchass.PurchasDetailProductListResult;

import java.util.List;

/**
 * @ClassName:ProductionPurchasServiceMapper
 * @description: ProductionPurchasServiceMapper
 * @author: Alex
 * @Version：1.3
 * @create: 2020/11/11 15:35 
 */
public interface ProductionPurchasServiceMapper {
    /**
    * @Description
    * @Author: alex
    * @Date:   2020/11/11 3:38 下午
    * @Param:  enter
    * @Return: int
    * @desc: 列表统计数据
    */
    int listCount(ProductionPurchasListEnter enter);
    /**
    * @Description
    * @Author: alex
    * @Date:   2020/11/11 3:38 下午
    * @Param:  enter
    * @Return:
    * @desc: 列表
    */
    List<ProductionPurchasListResult> list(ProductionPurchasListEnter enter);
    /**
    * @Description
    * @Author: alex
    * @Date:   2020/11/11 4:29 下午
    * @Param:  enter
    * @Return: List<PurchasDetailProductListResult>
    * @desc: 单据产品详情
    */
    List<PurchasDetailProductListResult> detailProductList(IdEnter enter);
}
