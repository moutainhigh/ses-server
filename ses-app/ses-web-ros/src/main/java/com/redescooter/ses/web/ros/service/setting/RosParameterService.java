package com.redescooter.ses.web.ros.service.setting;

import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.api.foundation.vo.setting.ParameterGroupResultList;
import com.redescooter.ses.api.foundation.vo.setting.ParameterResult;
import com.redescooter.ses.web.ros.vo.setting.ImportParameterEnter;
import com.redescooter.ses.web.ros.vo.setting.ImportParameterExcleData;
import com.redescooter.ses.web.ros.vo.setting.RosParameterListEnter;
import com.redescooter.ses.web.ros.vo.setting.RosSaveParamentEnter;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface RosParameterService {
    /**
     * 参数名称
     * @param enter
     * @return
     */
    PageResult<ParameterResult> list(RosParameterListEnter enter);

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
     * @param ids
     * @param response
     * @return
     */
    GeneralResult export(String ids, HttpServletResponse response);

    /**
     * 导出参数列表
     * @param enter
     * @return
     */
    GeneralResult importParament(ImportParameterEnter enter);

    /**
     * 参数保存编辑
     * @param enter
     * @return
     */
    GeneralResult save(RosSaveParamentEnter enter);

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
     * 参数批量保存
     * @param enter
     * @param successList
     * @return
     */
    GeneralResult saveParameterBatch(ImportParameterEnter enter, List<ImportParameterExcleData> successList);
}
