package com.redescooter.ses.web.ros.collectionUtilsTest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 26/12/2019 6:23 下午
 * @ClassName: RedisOne
 * @Function: TODO
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CollectionUtilTest {

    @Test
    public void disjunction(){
        List<Long> a=new ArrayList<>();
        a.add(1L);
        a.add(2L);

        List<Long> b=new ArrayList<>();
        a.add(3L);
        a.add(4L);

        Collection disjunction = CollectionUtils.disjunction(a,b);
        disjunction.forEach(item->{
            System.out.println(item);
        });


    }


}
