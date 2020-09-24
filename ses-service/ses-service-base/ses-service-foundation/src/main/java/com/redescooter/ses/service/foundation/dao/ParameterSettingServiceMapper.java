package com.redescooter.ses.service.foundation.dao;

import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.foundation.vo.setting.ParameterListEnter;
import com.redescooter.ses.api.foundation.vo.setting.ParameterResult;

import java.util.List;

public interface ParameterSettingServiceMapper {
    /**
     * 参数列表
     * @param enter
     * @return
     */
    List<ParameterResult> paramList(ParameterListEnter enter);

    /**
     * 详情
     * @param enter
     * @return
     */
    ParameterResult detail(IdEnter enter);
}
