package com.redescooter.ses.web.ros.service.wms.cn.fr;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.vo.bom.combination.CombinationListEnter;
import com.redescooter.ses.web.ros.vo.wms.cn.china.*;
import com.redescooter.ses.web.ros.vo.wms.cn.fr.FrStockCountResult;
import com.redescooter.ses.web.ros.vo.wms.cn.fr.FrTodayInOrOutStockCountResult;

import java.util.List;

/**
 * @description:
 * @author: Aleks
 * @create: 2020/12/31 11:56
 */
public interface FrWhService {

    /**
     *  出入库统计
     * @param enter
     * @return
     */
    List<FrTodayInOrOutStockCountResult> inOrOutCount(GeneralEnter enter);


    /**
     *  库存统计
     * @param enter
     * @return
     */
    FrStockCountResult stockCount(GeneralEnter enter);


    /**
     * 法国仓库车辆库存列表
     * @param enter
     * @return
     */
    PageResult<WmsFinishScooterListResult> frScooterList(WmsFinishScooterListEnter enter);


    /**
     * 法国仓库车辆库存详情
     * @param enter
     * @return
     */
    WmsfinishScooterDetailResult frScooterDetail(IdEnter enter);


    /**
     * 法国组装件库存列表
     * @param enter
     * @return
     */
    PageResult<WmsFinishCombinListResult> frCombinList(CombinationListEnter enter);


    /**
     * 法国组装件库存详情
     * @param enter
     * @return
     */
    WmsfinishCombinDetailResult frCombinDetail(IdEnter enter);


    /**
     * 法国部件的库存列表
     */
    PageResult<MaterialStockPartsListResult> frPartsList(MaterialStockPartsListEnter enter);


    /**
     * 法国部件的库存详情
     * @param enter
     * @return
     */
    MaterialpartsStockDetailResult frPartsDetail(IdEnter enter);



}
