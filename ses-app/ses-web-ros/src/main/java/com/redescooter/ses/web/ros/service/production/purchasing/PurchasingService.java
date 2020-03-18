package com.redescooter.ses.web.ros.service.production.purchasing;

import com.redescooter.ses.api.common.vo.BaseBuinessListResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.vo.production.ConsigneeResult;
import com.redescooter.ses.web.ros.vo.production.FactoryResult;
import com.redescooter.ses.web.ros.vo.production.purchasing.PurchasingListEnter;
import com.redescooter.ses.web.ros.vo.production.purchasing.PurchasingNodeResult;
import com.redescooter.ses.web.ros.vo.production.purchasing.PurchasingResult;
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
    List<BaseBuinessListResult> paymentType(GeneralEnter enter);

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
    List<FactoryResult> factoryList(GeneralEnter enter);

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
    PurchasingNodeResult purchasingNode(IdEnter enter);

    /**
     * @param enter
     * @return
     */
    GeneralResult export(IdEnter enter);

//     paymentDetail(IdEnter enter);
}
