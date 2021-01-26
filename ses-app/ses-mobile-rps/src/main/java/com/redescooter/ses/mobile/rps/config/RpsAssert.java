package com.redescooter.ses.mobile.rps.config;

import com.redescooter.ses.mobile.rps.exception.SesMobileRpsException;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;

/**
 * RPS项目断言工具类
 * @author assert
 * @date 2021/1/7 10:53
 */
public class RpsAssert {

    /**
     * obj == null throw SesMobileRpsException
     * @param obj
     * @param code
     * @param msg
     */
    public static void isNull(Object obj, Integer code, String msg) {
        if (obj == null) {
            throw new SesMobileRpsException(code, msg);
        }
    }

    /**
     * obj != null throw new SesMobileRpsException(code, msg);
     * @param obj
     * @param code
     * @param msg
     */
    public static void isNotNull(Object obj, Integer code, String msg) {
        if (obj != null) {
            throw new SesMobileRpsException(code, msg);
        }
    }

    /**
     * obj is blank throw SesMobileRpsException
     * @param obj
     * @param code
     * @param msg
     */
    public static void isBlank(String obj, Integer code, String msg) {
        if (StringUtils.isBlank(obj)) {
            throw new SesMobileRpsException(code, msg);
        }
    }

    /**
     * collection is empty throw SesMobileRpsException
     * @param collection
     * @param code
     * @param msg
     */
    public static void isEmpty(Collection collection, Integer code, String msg) {
        if (collection.isEmpty()) {
            throw new SesMobileRpsException(code, msg);
        }
    }

    /**
     * collection is not empty throw SesMobileRpsException
     * @param collection
     * @param code
     * @param msg
     */
    public static void isNotEmpty(Collection collection, Integer code, String msg) {
        if (!collection.isEmpty()) {
            throw new SesMobileRpsException(code, msg);
        }
    }

    /**
     * flag is true throw SesMobileRpsException
     * @param flag
     * @param code
     * @param msg
     */
    public static void isTrue(Boolean flag, Integer code, String msg) {
        if (flag) {
            throw new SesMobileRpsException(code, msg);
        }
    }

    /**
     * flag is false throw SesMobileRpsException
     * @param flag
     * @param code
     * @param msg
     */
    public static void isFalse(Boolean flag, Integer code, String msg) {
        if (!flag) {
            throw new SesMobileRpsException(code, msg);
        }
    }

}
