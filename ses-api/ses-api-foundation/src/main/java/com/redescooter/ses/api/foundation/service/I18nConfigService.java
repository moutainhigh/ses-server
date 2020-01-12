package com.redescooter.ses.api.foundation.service;


import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.foundation.vo.message.DeleteI18nConfigEnter;
import com.redescooter.ses.api.foundation.vo.message.GetI18nConfigEnter;
import com.redescooter.ses.api.foundation.vo.message.GetI18nConfigResult;
import com.redescooter.ses.api.foundation.vo.message.PageGetI18nConfigEnter;
import com.redescooter.ses.api.foundation.vo.message.SaveI18nConfigEnter;

import java.util.List;

/**
 * @description: I18nConfigService
 * @author: Darren
 * @create: 2019/04/30 10:53
 */
public interface I18nConfigService {

    /**
     * 多维度 获取多语言配置i18n
     *
     * @param enter
     * @return
     */
    List<GetI18nConfigResult> getI18nConfig(GetI18nConfigEnter enter);

    /**
     * 分页 多维度 获取多语言配置i18n
     *
     * @param enter
     * @return
     */
    PageResult<GetI18nConfigResult> getI18nConfigOfPage(PageGetI18nConfigEnter enter);

    /**
     * 删除多语言配置i18n
     *
     * @param deleteI18nConfigEnter
     * @return
     */
    void delete(DeleteI18nConfigEnter deleteI18nConfigEnter);

    /**
     * 添加多语言配置i18n
     *
     * @param saveI18nConfigEnter
     * @return
     */
    void save(SaveI18nConfigEnter saveI18nConfigEnter);

}
