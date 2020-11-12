package com.redescooter.ses.web.ros.dao.restproductionorder;

import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.ros.dm.OpeProductionScooterBom;
import com.redescooter.ses.web.ros.vo.bo.PartDetailDto;
import com.redescooter.ses.web.ros.vo.restproductionorder.assembly.*;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchass.PurchasDetailProductListResult;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    * @Date:   2020/11/12 7:17 下午
    * @Param:  enter
    * @Return: List<CountByStatusResult>
     * @desc: 类型统计
    */
    List<CountByStatusResult> countByType(GeneralEnter enter);
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
    * @Description
    * @Author: alex
    * @Date:   2020/11/12 4:47 下午
    * @Param:  ids
    * @Return: PartDetailDto
    * @desc: 部件详情列表
    */
    List<PurchasDetailProductListResult> partsDetail(Set<Long> ids);
    /**
    * @Description
    * @Author: alex
    * @Date:   2020/11/12 5:41 下午
    * @Param:  enter
    * @Return: OpeProductionScooterBom
    * @desc: 整车颜色序列号校验
    */
    List<OpeProductionScooterBom> getByGroupAndColorIds(List<Map<String,Object>> listMap);


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
