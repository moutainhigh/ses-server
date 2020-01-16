//package com.redescooter.ses.starter.redis;
//
///**
// * @author Mr.lijiating
// * @version V1.0
// * @Date: 15/1/2020 9:14 下午
// * @ClassName: RedisKeyUtil
// * @Function: 为了防止生成的key有冲突
// */
//
//public class RedisKeyUtil {
//    private static String SPLIT = ":";
//    private static String BIZ_LIKE = "LIKE";
//    private static String BIZ_DISLIKE = "DISLIKE";
//    private static String BIZ_EVENTQUEUE = "EVENTQUEUE";
//
//    //获取点赞的key
//    public static String getLikeKey(int entityType, int entityId){
//        return BIZ_LIKE + SPLIT + String.valueOf(entityType) + SPLIT +String.valueOf(entityId);
//    }
//
//    //获取点踩的key
//    public static String getDislikeKey(int entityType, int entityId){
//        return BIZ_DISLIKE +SPLIT + String.valueOf(entityType) + SPLIT + String.valueOf(entityId);
//    }
//
//    public static  String  getEventQueueKey(){
//        return BIZ_EVENTQUEUE;
//    }
//
//
//
//}
