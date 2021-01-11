package com.redescooter.ses.web.ros.service.wms.cn.china;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.IntResult;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.vo.bom.combination.CombinationListEnter;
import com.redescooter.ses.web.ros.vo.wms.cn.china.AbleProductionScooterResult;
import com.redescooter.ses.web.ros.vo.wms.cn.china.WmsFinishCombinListResult;
import com.redescooter.ses.web.ros.vo.wms.cn.china.WmsFinishScooterListEnter;
import com.redescooter.ses.web.ros.vo.wms.cn.china.WmsFinishScooterListResult;
import com.redescooter.ses.web.ros.vo.wms.cn.china.WmsStockCountEnter;
import com.redescooter.ses.web.ros.vo.wms.cn.china.WmsStockCountResult;
import com.redescooter.ses.web.ros.vo.wms.cn.china.WmsStockTypeEnter;
import com.redescooter.ses.web.ros.vo.wms.cn.china.WmsfinishCombinDetailResult;
import com.redescooter.ses.web.ros.vo.wms.cn.china.WmsfinishScooterDetailResult;

import java.util.List;
import java.util.Map;

/**
 * @description: 成品库服务层
 * @author: Aleks
 * @create: 2020/12/28 15:00
 */
public interface WmsFinishStockService {


    /**
     * 成品库车辆库存列表
     * @param enter
     * @return
     */
    PageResult<WmsFinishScooterListResult> finishScooterList(WmsFinishScooterListEnter enter);


    /**
     * 成品库车辆库存详情
     * @param enter
     * @return
     */
    WmsfinishScooterDetailResult finishScooterDetail(IdEnter enter);


    /**
     * 库存统计
     * @param enter
     * @return
     */
    WmsStockCountResult wmsStockCount(WmsStockCountEnter enter);


    /**
     * 成品库tab的数量统计（只有车辆和组装件）
     * @param enter
     * @return
     */
    Map<String, Integer> finishStockTabCount(WmsStockCountEnter enter);


    /**
     * 剩下的原料（部件）还可生产多少车
     * @param enter
     * @return
     */
    List<AbleProductionScooterResult> ableProductionScooter(GeneralEnter enter);


    /**
     * 成品库组装件库存列表
     * @param enter
     * @return
     */
    PageResult<WmsFinishCombinListResult> finishCombinList(CombinationListEnter enter);


    /**
     * 成品库组装件库存详情
     * @param enter
     * @return
     */
    WmsfinishCombinDetailResult finishCombinDetail(IdEnter enter);


    /**
     * 出库单和入库单的数量统计，按国家区分（从库存点击出入库管理进入的）
     * @param enter
     * @return
     */
    Map<String, Integer> outOrInOrderConunt(WmsStockTypeEnter enter);


    /**
     * 根据组装件id获得成品库组装件库存可用库存数量
     * @param enter
     * @return
     */
    IntResult getAbleStockQtyByProductionCombinBomId(IdEnter enter);
}
