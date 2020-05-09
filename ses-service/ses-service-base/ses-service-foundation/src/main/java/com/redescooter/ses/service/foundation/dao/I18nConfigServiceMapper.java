package com.redescooter.ses.service.foundation.dao;

import com.redescooter.ses.api.foundation.vo.message.GetI18nConfigEnter;
import com.redescooter.ses.api.foundation.vo.message.GetI18nConfigResult;
import com.redescooter.ses.api.foundation.vo.message.PageGetI18nConfigEnter;

import java.util.List;

/**
 * description: I18nConfigServiceMapper
 * author jerry.li
 * create: 2019-05-08 01:15
 */

public interface I18nConfigServiceMapper {

    /**
     * 多维度查询国际化多语言配置
     *
     * @param enter
     * @return
     */
    List<GetI18nConfigResult> getI18nConfig(GetI18nConfigEnter enter);

    /**
     * 分页多维度国际化多语言配置
     *
     * @param enter
     * @return
     */
    List<GetI18nConfigResult> getI18nConfigOfPage(PageGetI18nConfigEnter enter);

    /**
     * 获取多维度国际化配置条数
     *
     * @param enter
     * @return
     */
    Integer getI18nConfigTotalRows(GetI18nConfigEnter enter);


}
