package com.redescooter.ses.service.foundation.service.impl;

import com.redescooter.ses.service.common.i18n.I18nServiceMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

/**
 * @ClassName: I18nServiceMessageImpl
 * @Description: TODO
 * @Author: jerry.li
 * @CreateTime: 2019-07-08 16:23
 * @Version: 1.0
 */
@Slf4j
@DubboService
public class I18nServiceMessageImpl implements I18nServiceMessage {

    @Autowired
    private MessageSource messageSource;

    /**
     * @param code：对应文本配置的key.
     * @return 对应地区的语言消息字符串
     */
    @Override
    public String getMessage(String code) {
        return this.getMessage(code, new Object[]{});
    }

    @Override
    public String getMessage(String code, String defaultMessage) {
        return this.getMessage(code, null, defaultMessage);
    }

    @Override
    public String getMessage(String code, String defaultMessage, Locale locale) {
        return this.getMessage(code, null, defaultMessage, locale);
    }

    @Override
    public String getMessage(String code, Locale locale) {
        return this.getMessage(code, null, "", locale);
    }

    @Override
    public String getMessage(String code, Object[] args) {
        return this.getMessage(code, args, "");
    }

    @Override
    public String getMessage(String code, Object[] args, Locale locale) {
        return this.getMessage(code, args, "", locale);
    }

    @Override
    public String getMessage(String code, Object[] args, String defaultMessage) {
        Locale locale = LocaleContextHolder.getLocale();
        return this.getMessage(code, args, defaultMessage, locale);
    }

    @Override
    public String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
        return messageSource.getMessage(code, args, defaultMessage, locale);
    }

    /**
     * @Description: 实例
     * @param:
     * @auther: jerry
     * @date: 2019-07-08 16:35
     * @version: 1.0
     */
    public String getOLOMessage(String language, String country, String key) {
        Locale locale = new Locale(language, country);
        return messageSource.getMessage(key, null, locale);
    }
}
