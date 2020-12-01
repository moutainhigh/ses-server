package com.redescooter.ses.service.foundation.dao;

import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.foundation.vo.setting.ParameterListEnter;
import com.redescooter.ses.api.foundation.vo.setting.ParameterResult;
import org.apache.ibatis.annotations.Param;

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

    /**
     * 导出
     * @param ids
     * @param systemType
     * @return
     */
    List<ParameterResult> export(@Param("ids") List<Long> ids, @Param("systemType") String systemType);

    /**
     * 参数名 查询
     * @param parameterNames
     * @param systemType
     * @return
     */
    List<ParameterResult> checkParameterName(@Param("parameterNames") List<String> parameterNames, @Param("systemType") String systemType);
}
