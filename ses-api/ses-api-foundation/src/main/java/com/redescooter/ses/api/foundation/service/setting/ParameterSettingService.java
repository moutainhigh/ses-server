package com.redescooter.ses.api.foundation.service.setting;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.foundation.vo.setting.ParameterListEnter;
import com.redescooter.ses.api.foundation.vo.setting.ParameterResult;
import com.redescooter.ses.api.foundation.vo.setting.SaveParamentEnter;

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
    GeneralResult export(GeneralEnter enter);

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
}
