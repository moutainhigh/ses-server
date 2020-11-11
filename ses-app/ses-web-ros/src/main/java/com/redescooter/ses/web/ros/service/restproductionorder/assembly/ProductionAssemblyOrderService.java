package com.redescooter.ses.web.ros.service.restproductionorder.assembly;

import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.assembly.ProductionAssemblyOrderDetailResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.assembly.ProductionAssemblyOrderListEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.assembly.ProductionAssemblyOrderListResult;

/**
 * @ClassName:AssemblyPurchasOrderService
 * @description: AssemblyPurchasOrderService
 * @author: Alex
 * @Version：1.3
 * @create: 2020/11/10 14:09 
 */
public interface ProductionAssemblyOrderService {
    /**
    * @Description
    * @Author: alex
    * @Date:   2020/11/10 2:34 下午
    * @Param:  enter
    * @Return: ProductionAssemblyOrderListResult
    * @desc: 列表
    */
    PageResult<ProductionAssemblyOrderListResult> list(ProductionAssemblyOrderListEnter enter);


    ProductionAssemblyOrderDetailResult detail();


}
