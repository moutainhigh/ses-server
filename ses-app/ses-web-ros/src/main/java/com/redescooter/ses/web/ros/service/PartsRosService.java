package com.redescooter.ses.web.ros.service;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.StringEnter;
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
    GeneralResult savePartsList(List<ExpressPartsExcleData> list, ImportPartsEnter enter);

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
    GeneralResult adds(StringEnter enter);

    /**
     * 批量删除
     *
     * @param enter
     * @return
     */
    GeneralResult deletes(StringEnter enter);

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
    HistoryPartsResult history(IdEnter enter);

}