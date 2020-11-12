package com.redescooter.ses.web.ros.dao.restproductionorder;

import com.redescooter.ses.web.ros.vo.restproductionorder.assembly.AssemblyOrderDetailEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.assembly.ProductionAssemblyOrderDetailResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.assembly.ProductionAssemblyOrderListEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.assembly.ProductionAssemblyOrderListResult;

import java.util.List;

/**
 * @ClassName:ProductionAssemblyOrderServiceMapper
 * @description: ProductionAssemblyOrderServiceMapper
 * @author: Alex
 * @Version：1.3
 * @create: 2020/11/12 11:56 
 */
public interface ProductionAssemblyOrderServiceMapper {
    /**
    * @Description
    * @Author: alex
    * @Date:   2020/11/12 1:41 下午
    * @Param:  enter
    * @Return: int
    * @desc: 列表统计
    */
    int listCount(ProductionAssemblyOrderListEnter enter);
    /**
    * @Description
    * @Author: alex
    * @Date:   2020/11/12 1:42 下午
    * @Param:  列表
    * @Return: ProductionAssemblyOrderListResult
    * @desc: 列表
    */
    List<ProductionAssemblyOrderListResult> list(ProductionAssemblyOrderListEnter enter);
    /**
    * @Description
    * @Author: alex
    * @Date:   2020/11/12 1:55 下午
    * @Param:  enter
    * @Return: ProductionAssemblyOrderDetailResult
    * @desc: 单据详情
    */
    ProductionAssemblyOrderDetailResult detail(AssemblyOrderDetailEnter enter);
}
