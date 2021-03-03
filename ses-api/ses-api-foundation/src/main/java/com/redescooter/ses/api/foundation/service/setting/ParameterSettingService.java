package com.redescooter.ses.api.foundation.service.setting;

import com.redescooter.ses.api.common.vo.base.BooleanEnter;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.StringResult;
import com.redescooter.ses.api.foundation.vo.setting.ParameterGroupResultList;
import com.redescooter.ses.api.foundation.vo.setting.ParameterListEnter;
import com.redescooter.ses.api.foundation.vo.setting.ParameterListResult;
import com.redescooter.ses.api.foundation.vo.setting.ParameterResult;
import com.redescooter.ses.api.foundation.vo.setting.SaveParamentEnter;
import com.redescooter.ses.api.foundation.vo.setting.SaveParameterBatchEnter;

import java.util.List;
import java.util.Map;

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
     * @param
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

    /**
     * 根据分组名称获得此分组下的所有参数
     */
    List<ParameterListResult> getAllParamByGroup(IdEnter enter);

    /**
     * 获得所有分组的所有参数
     */
    Map<String, Map<String, String>> getAllGroupParam(GeneralEnter enter);

}
