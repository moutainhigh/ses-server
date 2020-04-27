package com.redescooter.ses.web.ros.collectionUtilsTest;

import lombok.extern.log4j.Log4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 26/12/2019 6:23 下午
 * @ClassName: RedisOne
 * @Function: TODO
 */
@Log4j
public class CollectionUtilTest {

    @Test
    public void disjunction() {


        System.out.println("Method one:" + Math.random() * 100);

        Random random = new Random();
        System.out.println("Method two:" + new Random().nextInt(100));

    }

    @Test
    public void stream() {

        System.out.println(DigestUtils.md5Hex("RedeScooter@2020" + 12727));
    }


}
