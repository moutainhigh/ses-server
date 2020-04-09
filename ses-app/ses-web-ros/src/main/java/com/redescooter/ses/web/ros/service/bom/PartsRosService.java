package com.redescooter.ses.web.ros.service;

import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.web.ros.vo.bom.QueryPartListEnter;
import com.redescooter.ses.web.ros.vo.bom.QueryPartListResult;
import com.redescooter.ses.web.ros.vo.bom.parts.*;

import java.util.List;

/**
 * @ClassName:BomRosService
 * @description: BomRosService
 * @author: Alex
 * @Version：1.2
 * @create: 2020/02/25 10:18
 */
public interface PartsRosService {

    /**
     * 公共状态统计
     *
     * @param enter
     * @return
     */
    MapResult commonCountStatus(GeneralEnter enter);

    /**
     * 部品类型列表
     *
     * @param enter
     * @return
     */
    List<PartsTypeResult> typeCount(GeneralEnter enter);

    /**
     * 导入零部件
     *
     * @param enter
     * @return
     */
    ImportExcelPartsResult importParts(ImportPartsEnter enter);

    /**
     * 导入零部件
     *
     * @param enter
     * @return
     */
    ImportExcelPartsResult savePartsList(List<ExpressPartsExcleData> list, ImportPartsEnter enter);

    /**
     * 批量编辑部件
     *
     * @param enter
     * @return
     */
    GeneralResult edits(StringEnter enter);

    /**
     * 批量新增部品
     *
     * @param enter
     * @return
     */
    List<DetailsPartsResult> iterations(StringEnter enter);

    /**
     * 批量删除
     *
     * @param enter
     * @return
     */
    GeneralResult deletes(StringEnter enter);

    /**
     * 查询部件是否绑定商品
     *
     * @param enter
     * @return
     */
    List<DeletePartResult> queryPartBindProduct(StringEnter enter);

    /**
     * 部品解绑商品
     *
     * @param enter
     * @return
     */
    GeneralResult partUnbind(PartUnbindEnter enter);

    /**
     * 详情
     *
     * @param enter
     * @return
     */
    DetailsPartsResult details(IdEnter enter);

    /**
     * 部件列表
     *
     * @param enter
     * @return
     */
    PageResult<DetailsPartsResult> list(PartListEnter enter);

    /**
     * 部品历史
     *
     * @param enter
     * @return
     */
    HistoryPartsResult historys(IdEnter enter);

    /**
     * 部品编辑完成进行同步
     * TODO 很重要
     *
     * @return
     */
    GeneralResult synchronizeParts(GeneralEnter enter);

    /**
     * 获取部件待同部数
     *
     * @param enter
     * @return
     */
    IntResult synchronizeNum(GeneralEnter enter);

    /**
     * @desc: 详情部件列表查询
     * @param: SaveScooterPartListEnter
     * @retrn: SaveScooterPartListResult
     * @auther: alex
     * @date: 2020/2/25 12:43
     * @Version: Ros 1.2
     */
    PageResult<QueryPartListResult> partList(QueryPartListEnter enter);

}