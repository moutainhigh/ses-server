package com.redescooter.ses.web.ros.dao.restproductionorder;

import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.ros.vo.bo.PartDetailDto;
import com.redescooter.ses.web.ros.vo.restproductionorder.inwhouse.InWhRelationOrderResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchaseorder.KeywordEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.purchass.*;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;
import java.util.Set;

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
    /**
    * @Description
    * @Author: alex
    * @Date:   2020/11/12 9:52 上午
    * @Param:  Collection
    * @Return: PartDetailDto
    * @desc: 部件详情列表
    */
    List<PartDetailDto> partDetailList(Collection<Long> partIds);
    /**
    * @Description
    * @Author: alex
    * @Date:   2020/11/12 10:32 上午
    * @Param:  enter
    * @Return: 可采购的产品列表
    * @desc:
    */
    int purchasPartListCount(PurchasPartListEnter enter);

    /**
    * @Description
    * @Author: alex
    * @Date:   2020/11/12 10:32 上午
    * @Param:  enter
    * @Return: PurchasPartListResult
    * @desc: 可采购的产品列表
    */
    List<PurchasPartListResult> purchasPartList(PurchasPartListEnter enter);
    /**
    * @Description
    * @Author: alex
    * @Date:   2020/11/12 11:04 上午
    * @Param:  enter
    * @Return: ProductionPurchasDetailResult
    * @desc: 单据详情
    */
    ProductionPurchasDetailResult detail(IdEnter enter);


    /**
     * @Author Aleks
     * @Description  获取生产采购单的部分数据
     * @Date  2020/11/12 16:52
     * @Param [enter]
     * @return
     **/
    List<InWhRelationOrderResult> relationOrderData(@Param("enter") KeywordEnter enter);
}
