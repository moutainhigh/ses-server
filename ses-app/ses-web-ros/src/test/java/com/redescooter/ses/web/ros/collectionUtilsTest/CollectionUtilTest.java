package com.redescooter.ses.web.ros.collectionUtilsTest;

import com.redescooter.ses.tool.utils.parts.ESCUtils;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

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
    public void set() {
        Set<String> productN = new HashSet<>();
        productN.add("a");
        productN.add("b");
        productN.add("a");
//        Iterator<String> iterator = productN.iterator();
//        while (iterator.hasNext()) {
//            System.out.println(iterator.next());
//        }
        productN.forEach(item -> {
            System.out.println(item);
        });
    }


}
