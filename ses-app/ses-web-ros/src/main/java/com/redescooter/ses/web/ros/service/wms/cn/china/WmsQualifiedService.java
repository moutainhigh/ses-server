package com.redescooter.ses.web.ros.service.wms.cn.china;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.vo.bom.combination.CombinationListEnter;
import com.redescooter.ses.web.ros.vo.wms.cn.china.*;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Map;

/**
 * @description:
 * @author: Aleks
 * @create: 2020/12/30 11:34
 */
public interface WmsQualifiedService {

    /**
     * 不合格品库车辆库存列表
     * @param enter
     * @return
     */
    PageResult<WmsQualifiedScooterListResult> scooterList(WmsFinishScooterListEnter enter);


    /**
     * 不合格品库车辆库存详情
     * @param enter
     * @return
     */
    WmsfinishScooterDetailResult scooterDetail(IdEnter enter);


    /**
     * 不合格品库组装件库存列表
     * @param enter
     * @return
     */
    PageResult<WmsQualifiedCombinListResult> combineList(CombinationListEnter enter);


    /**
     * 不合格品库组装件库存详情
     * @param enter
     * @return
     */
    WmsfinishCombinDetailResult combinDetail(IdEnter enter);


    /**
     * 不合格品库部件库存列表
     * @param enter
     * @return
     */
    PageResult<WmsQualifiedPartsListResult> partsList(MaterialStockPartsListEnter enter);


    /**
     * 不合格品库部件库存详情
     * @param enter
     * @return
     */
    MaterialpartsStockDetailResult partsDetail(IdEnter enter);


    /**
     *  不合格品库库存统计
     * @param enter
     * @return
     */
    WmsQualifiedQtyCountResult quailifiedQtyCount(WmsQualifiedQtyCountEnter enter);


    /**
     * 不合格品库tab的数量统计（车辆/组装件/部件）
     * @param enter
     * @return
     */
    Map<String, Integer> unailifiedStockTabCount(GeneralEnter enter);
}
