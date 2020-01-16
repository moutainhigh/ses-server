//package com.redescooter.ses.starter.redis.queue;
//
///**
// * @author Mr.lijiating
// * @version V1.0
// * @Date: 15/1/2020 9:11 下午
// * @ClassName: EventQueueModel
// * @Function: 不同的事件肯定是有不同的类型的
// */
//
//import com.redescooter.ses.starter.redis.enums.EventQueueType;
//
//import java.util.HashMap;
//import java.util.Map;
//
//
//public class EventQueueModel {
//    //例如，有人评论了一个问题，那type就是评论， actorId就是谁评论的，
//    // entityId和entityType就是评论的是那个问题，entityOwnerId就是那个问题关联的对象
//    private EventQueueType type;    //事件的类型
//    private int actorId;   //事件的触发者
//    private int entityType;    //触发事件的载体
//    private int entityId;  //和entityType组合成触发事件的载体  可以使任何一个实体的id，问题，评论，用户，站内信等等
//    private int entityOwnerId;         //载体关联的对象,当我们给一个人点赞时，系统要给那个人（也就是entityOwnerId）发送一个站内信，通知那个人他被点赞了。
//
//    public EventQueueModel() {
//
//    }
//
//    public EventQueueModel(EventQueueType type) {
//        this.type = type;
//    }
//
//    //定义可扩展的字段
//    private Map<String, String> exts = new HashMap<>();
//
//    public EventQueueModel setExts(String key, String value) {
//        exts.put(key, value);
//        return this;
//    }
//
//    public String getExts(String key) {
//        return exts.get(key);
//    }
//
//    public EventQueueType getType() {
//        return type;
//    }
//
//    //为了能够实现链状的设置
//    public EventQueueModel setType(EventQueueType type) {
//        this.type = type;
//        return this;      //这个就是为了实现这个xxx.setType().setXX();
//    }
//
//    public int getActorId() {
//        return actorId;
//    }
//
//    public EventQueueModel setActorId(int actorId) {
//        this.actorId = actorId;
//        return this;
//    }
//
//    public int getEntityType() {
//        return entityType;
//    }
//
//    public EventQueueModel setEntityType(int entityType) {
//        this.entityType = entityType;
//        return this;
//    }
//
//    public int getEntityId() {
//        return entityId;
//    }
//
//    public EventQueueModel setEntityId(int entityId) {
//        this.entityId = entityId;
//        return this;
//    }
//
//    public int getEntityOwnerId() {
//        return entityOwnerId;
//    }
//
//    public EventQueueModel setEntityOwnerId(int entityOwnerId) {
//        this.entityOwnerId = entityOwnerId;
//        return this;
//    }
//
//    public Map<String, String> getExts() {
//        return exts;
//    }
//
//    public EventQueueModel setExts(Map<String, String> exts) {
//        this.exts = exts;
//        return this;
//    }
//
//}