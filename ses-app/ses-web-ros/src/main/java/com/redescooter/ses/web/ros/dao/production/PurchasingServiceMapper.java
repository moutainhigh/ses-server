package com.redescooter.ses.web.ros.dao.production;

import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.ros.vo.production.purchasing.PaymentItemDetailResult;
import com.redescooter.ses.web.ros.vo.production.purchasing.PruchasingItemListEnter;
import com.redescooter.ses.web.ros.vo.production.purchasing.PruchasingItemResult;
import com.redescooter.ses.web.ros.vo.production.purchasing.PurchasingListEnter;
import com.redescooter.ses.web.ros.vo.production.purchasing.PurchasingNodeResult;
import com.redescooter.ses.web.ros.vo.production.purchasing.PurchasingResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName:PurchasingServiceMapper
 * @description: PurchasingServiceMapper
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/20 14:32
 */
public interface PurchasingServiceMapper {
    /**
     * 采购商品列表
     *
     * @param enter
     * @return
     */
    List<PruchasingItemResult> pruchasingProductList(@Param("enter") PruchasingItemListEnter enter, @Param("productTypeList") List<String> productTypeList);

    /**
     * 根据类型统计
     *
     * @param enter
     * @return
     */
    List<CountByStatusResult> countByType(GeneralEnter enter);

    /**
     * 采购单列表
     *
     * @param enter
     * @param statusList
     * @return
     */
    int purchasingListCount(@Param("enter") PurchasingListEnter enter, @Param("statusList") List<String> statusList);

    /**
     * 采购单列表
     *
     * @param enter
     * @param statusList
     * @return
     */
    List<PurchasingResult> purchasingList(@Param("enter") PurchasingListEnter enter, @Param("statusList") List<String> statusList);

    /**
     * 采购单付款条目列表
     *
     * @param ids
     * @return
     */
    List<PaymentItemDetailResult> paymentItemList(List<Long> ids);

    /**
     * 采购单详情
     *
     * @param enter
     * @return
     */
    PurchasingResult detail(IdEnter enter);

    /**
     * 采购订单节点
     *
     * @param enter
     * @return
     */
    List<PurchasingNodeResult> purchasingNode(IdEnter enter);
}
