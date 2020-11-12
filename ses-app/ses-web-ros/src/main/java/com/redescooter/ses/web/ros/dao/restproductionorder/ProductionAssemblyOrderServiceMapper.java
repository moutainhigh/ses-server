package com.redescooter.ses.web.ros.dao.restproductionorder;

import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.assembly.*;
import com.redescooter.ses.web.ros.vo.restproductionorder.inwhouse.InWhRelationOrderResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.inwhouse.SaveOrUpdateCombinBEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.inwhouse.SaveOrUpdateScooterBEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchaseorder.KeywordEnter;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
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
    /**
    * @Description
    * @Author: alex
    * @Date:   2020/11/12 3:08 下午
    * @Param:  enter
    * @Return: AssemblyDetailProductListResult
    * @desc: 产品车辆列表
    */
    List<AssemblyDetailProductListResult> productScooterList(AssemblyOrderDetailEnter enter);
    /**
    * @Description
    * @Author: alex
    * @Date:   2020/11/12 3:09 下午
    * @Param:  enter
    * @Return: List<AssemblyDetailProductListResult>
    * @desc: 产品组装件列表
    */
    List<AssemblyDetailProductListResult> productCombinList(AssemblyOrderDetailEnter enter);


    /**
     * @Author Aleks
     * @Description  获取组装单的部分数据
     * @Date  2020/11/12 16:52
     * @Param [enter]
     * @return
     **/
    List<InWhRelationOrderResult> relationOrderData(@Param("enter") KeywordEnter enter);


    /**
     * @Author Aleks
     * @Description  通过组装单的id 获取组装单的组装件产品信息
     * @Date  2020/11/12 18:09
     * @Param
     * @return
     **/
    List<SaveOrUpdateCombinBEnter> relationCombinOrderCombinData(@Param("enter") IdEnter enter);


    /**
     * @Author Aleks
     * @Description  通过组装单的id 获取组装单的整车产品信息
     * @Date  2020/11/12 18:14
     * @Param [enter]
     * @return
     **/
    List<SaveOrUpdateScooterBEnter> relationCombinOrderScooterData(@Param("enter")IdEnter enter);
}
