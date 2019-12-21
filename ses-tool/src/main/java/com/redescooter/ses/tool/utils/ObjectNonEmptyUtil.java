package com.redescooter.ses.tool.utils;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 20/11/2019 4:05 下午
 * @ClassName: ArrayUtil
 * @Function: 判断 数组/集合 为空的工具类
 */
public class ObjectNonEmptyUtil<T> {

    //判断集合是否为空
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    //判断Map是否为空
    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    //判断数组是否为空
    public static boolean isEmpty(Object[] array) {
        return array == null || array.length == 0;
    }

    //判断List是否为空
    public static boolean isEmpty(List<Object> list) {
        return list == null || list.size() == 0;
    }

    //判断实体类列表是否为空
    public static boolean isNotEmpty(Collection<?> collection) {
        return org.apache.commons.collections.CollectionUtils.isNotEmpty(collection);
    }

}