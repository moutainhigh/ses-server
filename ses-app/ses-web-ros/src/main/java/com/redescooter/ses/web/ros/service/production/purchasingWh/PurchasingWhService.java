package com.redescooter.ses.web.ros.service.production.purchasingWh;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.vo.production.wh.AssemblyProductResult;
import com.redescooter.ses.web.ros.vo.production.wh.AvailableListResult;
import com.redescooter.ses.web.ros.vo.production.wh.OutWhResult;
import com.redescooter.ses.web.ros.vo.production.wh.QcingListResult;
import com.redescooter.ses.web.ros.vo.production.wh.TobeStoredResult;
import com.redescooter.ses.web.ros.vo.production.wh.WasteResult;
import com.redescooter.ses.web.ros.vo.production.wh.WhEnter;

import java.util.List;
import java.util.Map;

/**
 * @ClassName:PurchasingWh
 * @description: PurchasingWh
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/03 14:15
 */
public interface PurchasingWhService {

    /**
     * 类型统计
     *
     * @param enter
     * @return
     */
    Map<String, Integer> countByType(GeneralEnter enter);

    /**
     * 类型 列表
     *
     * @param enter
     * @return
     */
    Map<String, String> typeList(GeneralEnter enter);

    /**
     * 可用列表
     *
     * @param enter
     * @return
     */
    PageResult<AvailableListResult> availableList(WhEnter enter);

    /**
     * 质检中列表
     *
     * @param enter
     * @return
     */
    PageResult<QcingListResult> qcingList(WhEnter enter);

    /**
     * 待入库列表
     *
     * @param enter
     * @return
     */
    PageResult<TobeStoredResult> tobeStoredList(WhEnter enter);

    /**
     * 出库列表
     *
     * @param enter
     * @return
     */
    PageResult<OutWhResult> outWhList(WhEnter enter);

    /**
     * 废料列表
     *
     * @param enter
     * @return
     */
    PageResult<WasteResult> wasteList(WhEnter enter);

    /**
     * 可组装的产品列表
     *
     * @param enter
     * @return
     */
    List<AssemblyProductResult> canAssemblyProductList(GeneralEnter enter);
}
