package com.redescooter.ses.starter.redis.queue;

import com.redescooter.ses.starter.redis.vo.QueueMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 15/1/2020 9:04 下午
 * @ClassName: RedisTaskContainer
 * @Function: 一般容器类的设计最为重要, 作用是作为spring bean 拿到spring管理的相关bean, 同时可以管理多个队列等.
 */
@Slf4j
@Component
public class RedisTaskContainer {

    //Runtime.getRuntime().availableProcessors()
    private static int runTaskThreadNum = 2;

    //使用一个统一维护的线程池来管理隔离线程
    private static ExecutorService es = Executors.newFixedThreadPool(runTaskThreadNum);

    @Resource
    private RedisTemplate redisTemplate;

    public static String ORDER_SEND_SAVE = "order:send:save";

    //队列里边的数据泛型可以根据实际情况调整, 可以定义多个类似的队列
    private RedisQueue<Map<String, List<QueueMessage>>> redisQueue = null;

    @PostConstruct
    private void init() {
        redisQueue = new RedisQueue(redisTemplate, ORDER_SEND_SAVE);

        Consumer<Map<String, List<QueueMessage>>> consumer = (data) -> {
            // do something
            log.info("do something........");
        };

        //提交线程
        for (int i = 0; i < runTaskThreadNum; i++) {
            es.execute(
                    new SendRedisConsumer(this, consumer)
            );
        }
    }

    public RedisQueue<Map<String, List<QueueMessage>>> getRedisQueue() {
        return redisQueue;
    }

}