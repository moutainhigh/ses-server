//package com.redescooter.ses.starter.redis.queue;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.List;
//import java.util.Map;
//import java.util.function.Consumer;
//
///**
// * @author Mr.lijiating
// * @version V1.0
// * @Date: 15/1/2020 9:05 下午
// * @ClassName: OrderSendRedisConsumer
// * @Function: TODO
// */
//public class OrderSendRedisConsumer extends Thread {
//
//    private Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    private RedisTaskContainer container;
//
//    private Consumer<Map<String, List<EventQueueModel>>> consumer;
//
//    public OrderSendRedisConsumer(RedisTaskContainer container, Consumer<Map<String, List<EventQueueModel>>> consumer){
//        this.container = container;
//        this.consumer = consumer;
//    }
//
//    @Override
//    public void run() {
//        try{
//            while(true){
//                Map<String, List<EventQueueModel>> value = container.getRedisQueue().takeFromTail();//cast exception? you should check.
//                //逐个执行
//                if(value != null){
//                    try{
//                        consumer.accept(value);
//                    }catch(Exception e){
//                        logger.error("调用失败", e);
//                    }
//                }
//            }
//        }catch(Exception e){
//            logger.error("轮循线程异常退出", e);
//        }
//    }
//}