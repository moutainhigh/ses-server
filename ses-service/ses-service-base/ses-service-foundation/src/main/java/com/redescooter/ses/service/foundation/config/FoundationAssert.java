package com.redescooter.ses.service.foundation.config;

import com.redescooter.ses.api.foundation.exception.FoundationException;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;

/**
 * Foundation项目断言工具类
 * @Date 2021/2/9 1:37 下午
 * @date 2021/1/7 10:53
 */
public class FoundationAssert {

    /**
     * obj == null throw FoundationException
     * @param obj
     * @param code
     * @param msg
     */
    public static void isNull(Object obj, Integer code, String msg) {
        if (obj == null) {
            throw new FoundationException(code, msg);
        }
    }

    /**
     * obj != null throw new FoundationException(code, msg);
     * @param obj
     * @param code
     * @param msg
     */
    public static void isNotNull(Object obj, Integer code, String msg) {
        if (obj != null) {
            throw new FoundationException(code, msg);
        }
    }

    /**
     * obj is blank throw FoundationException
     * @param obj
     * @param code
     * @param msg
     */
    public static void isBlank(String obj, Integer code, String msg) {
        if (StringUtils.isBlank(obj)) {
            throw new FoundationException(code, msg);
        }
    }

    /**
     * obj is not blank throw FoundationException
     * @param obj
     * @param code
     * @param msg
     */
    public static void isNotBlank(String obj, Integer code, String msg) {
        if (StringUtils.isNotBlank(obj)) {
            throw new FoundationException(code, msg);
        }
    }

    /**
     * collection is empty throw FoundationException
     * @param collection
     * @param code
     * @param msg
     */
    public static void isEmpty(Collection collection, Integer code, String msg) {
        if (collection.isEmpty()) {
            throw new FoundationException(code, msg);
        }
    }

    /**
     * collection is not empty throw FoundationException
     * @param collection
     * @param code
     * @param msg
     */
    public static void isNotEmpty(Collection collection, Integer code, String msg) {
        if (!collection.isEmpty()) {
            throw new FoundationException(code, msg);
        }
    }

    /**
     * flag is true throw FoundationException
     * @param flag
     * @param code
     * @param msg
     */
    public static void isTrue(Boolean flag, Integer code, String msg) {
        if (flag) {
            throw new FoundationException(code, msg);
        }
    }

    /**
     * flag is false throw FoundationException
     * @param flag
     * @param code
     * @param msg
     */
    public static void isFalse(Boolean flag, Integer code, String msg) {
        if (!flag) {
            throw new FoundationException(code, msg);
        }
    }

}
