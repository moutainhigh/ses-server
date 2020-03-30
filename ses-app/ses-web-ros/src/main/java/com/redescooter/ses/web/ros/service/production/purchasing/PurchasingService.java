package com.redescooter.ses.web.ros.service.production.purchasing;

import com.redescooter.ses.api.common.vo.NodeResult;
import com.redescooter.ses.api.common.vo.SaveNodeEnter;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.vo.production.ConsigneeResult;
import com.redescooter.ses.web.ros.vo.production.FactoryCommonResult;
import com.redescooter.ses.web.ros.vo.production.purchasing.PayEnter;
import com.redescooter.ses.web.ros.vo.production.purchasing.PaymentDetailResullt;
import com.redescooter.ses.web.ros.vo.production.purchasing.PruchasingItemListEnter;
import com.redescooter.ses.web.ros.vo.production.purchasing.PruchasingItemResult;
import com.redescooter.ses.web.ros.vo.production.purchasing.PurchasingListEnter;
import com.redescooter.ses.web.ros.vo.production.purchasing.PurchasingResult;
import com.redescooter.ses.web.ros.vo.production.purchasing.QcInfoResult;
import com.redescooter.ses.web.ros.vo.production.purchasing.QcItemListEnter;
import com.redescooter.ses.web.ros.vo.production.purchasing.QueryFactorySupplierResult;
import com.redescooter.ses.web.ros.vo.production.purchasing.SaveFactoryAnnexEnter;
import com.redescooter.ses.web.ros.vo.production.purchasing.SavePurchasingEnter;

import java.util.List;
import java.util.Map;

/**
 * @ClassName:PurchasingService
 * @description: PurchasingService
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/18 16:52
 */
public interface PurchasingService {
    /**
     * 采购单状态统计
     *
     * @retrn
     */
    Map<String, Integer> countByType(GeneralEnter enter);

    /**
     * 状态集合
     *
     * @param enter
     * @return
     */
    Map<String, String> statusList(GeneralEnter enter);

    /**
     * 采购单列表
     *
     * @param enter
     * @return
     */
    PageResult<PurchasingResult> list(PurchasingListEnter enter);

    /**
     * 付款方式
     *
     * @return
     */
    Map<String, String> paymentType(GeneralEnter enter);

    /**
     * 保存采购单
     *
     * @param enter
     * @return
     */
    GeneralResult save(SavePurchasingEnter enter);

    /**
     * 收件人列表
     *
     * @param enter
     * @return
     */
    List<ConsigneeResult> consigneeList(GeneralEnter enter);

    /**
     * 工厂列表
     *
     * @param enter
     * @return
     */
    List<FactoryCommonResult> factoryList(GeneralEnter enter);

    /**
     * 采购单详情
     *
     * @param enter
     * @return
     */
    PurchasingResult detail(IdEnter enter);

    /**
     * 采购单节点
     *
     * @param enter
     * @return
     */
    List<NodeResult> purchasingNode(IdEnter enter);

    /**
     * 采购单信息导出
     *
     * @param enter
     * @return
     */
    GeneralResult export(IdEnter enter);

    /**
     * 付款详情
     *
     * @param enter
     * @return
     */
    PaymentDetailResullt paymentDetail(IdEnter enter);

    /**
     * 支付入参
     *
     * @param enter
     * @return
     */
    GeneralResult pay(PayEnter enter);

    /**
     * 供应商列表
     *
     * @param enter
     * @return
     */
    List<FactoryCommonResult> supplierList(GeneralEnter enter);

    /**
     * 产品类型
     *
     * @param enter
     * @return
     */
    Map<String, String> productType(GeneralEnter enter);

    /**
     * 查询可采购的商品列表
     *
     * @param enter
     * @return
     */
    List<PruchasingItemResult> queryPurchasProductList(PruchasingItemListEnter enter);

    /**
     * 采购单商品列表
     *
     * @param enter
     * @return
     */
    List<PruchasingItemResult> pruchasingDetailProductList(IdEnter enter);

    /**
     * 查询采购单代工厂供应商
     *
     * @return
     */
    QueryFactorySupplierResult queryFactorySupplier(IdEnter enter);

    /**
     * 保存 工厂附件
     *
     * @param enter
     * @return
     */
    GeneralResult saveFactoryAnnex(SaveFactoryAnnexEnter enter);

    /**
     * 开始qc 质检
     *
     * @param enter
     * @return
     */
    GeneralResult startQc(IdEnter enter);

    /**
     * 再次qc 质检
     *
     * @param enter
     * @return
     */
    GeneralResult againQc(IdEnter enter);

    /**
     * qc完成
     *
     * @param enter
     * @return
     */
    GeneralResult completeQc(IdEnter enter);

    /**
     * 入库
     *
     * @param enter
     * @return
     */
    GeneralResult purchasingInWh(IdEnter enter);


    /**
     * qc状态
     *
     * @return
     */
    Map<String, Integer> qcCountByStatus(IdEnter enter);

    /**
     * QC 条目list
     *
     * @param enter
     * @return
     */
    List<QcInfoResult> qcList(QcItemListEnter enter);

    /**
     * QC 未通过数据导出
     *
     * @param enter
     * @return
     */
    GeneralResult qcFailExport(IdEnter enter);

    /**
     * 保存采购单节点
     *
     * @param enter
     * @return
     */
    GeneralResult savePurchasingNode(SaveNodeEnter enter);

    /**
     * 退货 （退掉质检未通过的）
     *
     * @param enter
     * @return
     */
    GeneralResult returnedPurchase(IdEnter enter);

    /**
     * 取消 采购单
     *
     * @param enter
     * @return
     */
    GeneralResult cancel(IdEnter enter);
}

