package com.redescooter.ses.web.ros.collectionUtilsTest;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.RandomUtil;
import com.redescooter.ses.api.common.constant.JedisConstant;
import com.redescooter.ses.starter.redis.service.JedisService;
import com.redescooter.ses.tool.utils.parts.ESCUtils;
import lombok.extern.log4j.Log4j;
import org.apache.dubbo.config.annotation.Reference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

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
}
