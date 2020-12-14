package com.redescooter.ses.api.foundation.service.setting;

import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.api.foundation.vo.setting.*;

import java.util.List;

public interface ParameterSettingService {

    /**
     * 参数名称
     * @param enter
     * @return
     */
    PageResult<ParameterResult> list(ParameterListEnter enter);

    /**
     * 详情
     * @param enter
     * @return
     */
    ParameterResult detail(IdEnter enter);

    /**
     * 删除
     * @param enter
     * @return
     */
    GeneralResult delete(IdEnter enter);

    /**
     * 导出参数列表
     * @param enter
     * @return
     */
    List<ParameterResult> export(List<Long> ids, String systemType);

    /**
     * 导出参数列表
     * @param enter
     * @return
     */
    GeneralResult importParament(GeneralEnter enter);

    /**
     * 参数保存编辑
     * @param enter
     * @return
     */
    GeneralResult save(SaveParamentEnter enter);

    /**
     * 下载模版
     * @param enter
     * @return
     */
    StringResult downloadExcel(GeneralEnter enter);

    /**
     * 分组列表
     * @param enter
     * @return
     */
    List<ParameterGroupResultList> groupList(BooleanEnter enter);

    /**
     * 参数名查询
     * @param parameterNames
     * @param systemType
     * @return
     */
    List<ParameterResult> checkParameterName(List<String> parameterNames, String systemType);

    /**
     * 批量保存参数列表
     * @param saveParameterBatchEnterList
     * @param systemType
     */
    void saveParameterBatchByImport(List<SaveParameterBatchEnter> saveParameterBatchEnterList, String systemType);
}
