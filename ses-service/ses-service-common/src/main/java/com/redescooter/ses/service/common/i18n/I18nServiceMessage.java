package com.redescooter.ses.service.common.i18n;

import java.util.Locale;

/**
 * @ClassName: I18nServiceMessage
 * @Description: TODO
 * @Author: jerry.li
 * @CreateTime: 2019-07-08 16:21
 * @Version: 1.0
 */

public interface I18nServiceMessage {

    /**
     * @param code：对应文本配置的key.
     * @return 对应地区的语言消息字符串
     */
    String getMessage(String code);

    String getMessage(String code, String defaultMessage);

    String getMessage(String code, String defaultMessage, Locale locale);

    String getMessage(String code, Locale locale);

    String getMessage(String code, Object[] args);

    String getMessage(String code, Object[] args, Locale locale);

    String getMessage(String code, Object[] args, String defaultMessage);

    String getMessage(String code, Object[] args, String defaultMessage, Locale locale);

}
