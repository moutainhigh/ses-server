package com.redescooter.ses.web.ros.collectionUtilsTest;

import com.redescooter.ses.tool.utils.parts.ESCUtils;
import org.junit.Test;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 26/12/2019 6:23 下午
 * @ClassName: RedisOne
 * @Function: TODO
 */

public class CollectionUtilTest {

    @Test
    public void disjunction() {
        String checkESC = ESCUtils.checkESC("90");
        System.out.println(checkESC);
    }

    @Test
    public void stream() {
        int[] arr = new int[]{1, 1, 3, 4, 5};
        IntStream intStream = Arrays.stream(arr);
//        intStream.forEach(System.out::println);
//        intStream.map(item-> item+2).allMatch((x) -> System.out.println(x>4));
    }

}
