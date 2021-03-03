package com.redescooter.ses.tool.utils.list;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Chris
 */
public class ListUtils {

    /**
     * 获取两个List的不同元素 无序
     */
    public static List<String> getDifferent(List<String> list1, List<String> list2) {
        List<String> diff = new ArrayList<>();
        List<String> maxList = list1;
        List<String> minList = list2;
        if (list2.size() > list1.size()) {
            maxList = list2;
            minList = list1;
        }
        Map<String, Integer> map = new HashMap<>(maxList.size());
        for (String string : maxList) {
            map.put(string, 1);
        }
        for (String string : minList) {
            if (map.get(string) != null) {
                map.put(string, 2);
                continue;
            }
            diff.add(string);
        }
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue() == 1) {
                diff.add(entry.getKey());
            }
        }
        return diff;
    }

    /**
     * 获取两个List的不同元素 有序
     */
    public static List<String> getDifferent2(List<String> allList, List<String> subList) {
        List<String> diff = Lists.newLinkedList();
        for (String str : allList) {
            if (!subList.contains(str)) {
                diff.add(str);
            }
        }
        return diff;
    }

}
