package com.redescooter.ses.web.ros.collectionUtilsTest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.redescooter.ses.tool.utils.parts.ESCUtils;
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

public class CollectionUtilTest {

    @Test
    public void disjunction() {
        String checkESC = ESCUtils.checkESC("90");
        System.out.println(checkESC);
    }


}
