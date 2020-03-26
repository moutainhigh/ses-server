package com.redescooter.ses.web.ros.dao.production;

import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.ros.dm.PartDetailDto;
import com.redescooter.ses.web.ros.vo.production.purchasing.PaymentItemDetailResult;
import com.redescooter.ses.web.ros.vo.production.purchasing.PruchasingItemListEnter;
import com.redescooter.ses.web.ros.vo.production.purchasing.PruchasingItemResult;
import com.redescooter.ses.web.ros.vo.production.purchasing.PurchasSupplierResult;
import com.redescooter.ses.web.ros.vo.production.purchasing.PurchasingListEnter;
import com.redescooter.ses.web.ros.vo.production.purchasing.PurchasingNodeResult;
import com.redescooter.ses.web.ros.vo.production.purchasing.PurchasingResult;
import com.redescooter.ses.web.ros.vo.production.purchasing.QcInfoResult;
import com.redescooter.ses.web.ros.vo.production.purchasing.QcItemDetailResult;
import com.redescooter.ses.web.ros.vo.production.purchasing.QcItemListEnter;
import com.redescooter.ses.web.ros.vo.production.purchasing.QueryFactorySupplierResult;
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
    List<PruchasingItemResult> queryPurchasProductList(@Param("enter") PruchasingItemListEnter enter, @Param("productTypeList") List<String> productTypeList);

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

    /**
     * 采购单商品列表
     *
     * @param enter
     * @return
     */
    List<PruchasingItemResult> pruchasingDetailProductList(IdEnter enter);

    /**
     * @param enter
     * @param scooterValue
     * @return
     */
    List<PruchasingItemResult> queryPurchasScooter(@Param("enter") PruchasingItemListEnter enter, @Param("scooterValue") String scooterValue);

    /**
     * 查询部件详情
     *
     * @param partsIds
     * @return
     */
    List<PartDetailDto> partDetailById(List<Long> partsIds);

    /**
     * 查询所有商品的部品
     *
     * @param partIds
     * @return
     */
    List<PruchasingItemResult> queryProductPartItemByProductIds(List<Long> partIds);

    /**
     * @param enter
     * @return
     */
    List<QcInfoResult> qcPartListByPurchasId(QcItemListEnter enter);

    /**
     * QC 质检条目详情
     *
     * @param enter
     * @param purshasBIds
     * @return
     */
    List<QcItemDetailResult> qcItemDetailList(@Param("enter") QcItemListEnter enter, @Param("purshasBIds") List<Long> purshasBIds);

    /**
     * 查询采购单代工厂
     *
     * @param enter
     * @return
     */
    QueryFactorySupplierResult queryFactoryByPurchasId(IdEnter enter);

    /**
     * 查询采购单供应商
     *
     * @param enter
     * @return
     */
    List<PurchasSupplierResult> purchasSupplierListByPurchasId(IdEnter enter);
}
