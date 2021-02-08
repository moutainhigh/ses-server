package com.redescooter.ses.service.common.service.impl;


import cn.hutool.core.lang.Snowflake;
import com.redescooter.ses.api.foundation.service.base.SequenceService;
import com.redescooter.ses.service.common.utils.SnowflakeIdWorker;
import com.redescooter.ses.starter.common.service.IdAppService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;

/**
 * @description: IdAppServiceImpl
 * @author: Darren
 * @create: 2019/08/16 14:39
 */
@Slf4j
@Service
public class IdAppServiceImpl implements IdAppService {

    @DubboReference
    private SequenceService sequenceService;

    @Override
    public long getId(String tableName) {
        try {
//            log.info("采用雪花算法");
            // 糊涂工具自带的雪花算法
//            Snowflake snowflake = new Snowflake(0,0);
//            return snowflake.nextId();
            SnowflakeIdWorker snowflakeIdWorker = SnowflakeIdWorker.getInstance();
            String idStr  = String.valueOf(snowflakeIdWorker.nextId());
            // 想要后面的15位 （为了防止第一位是0 只截取14位  然后前面拼接“1”）
            Long id = Long.parseLong("1" + idStr.substring(idStr.length()-14));
            return id;
//            return sequenceService.get(tableName);
        } catch (Exception e) {
            log.error("Get id  failure, tableName:" + tableName, e);
            throw new NullPointerException("Get id  failure, tableName:" + tableName);
        }
    }


    @SneakyThrows
    public static void main(String[] args) {
//        Snowflake snowflake = new Snowflake(0,0);
//        System.out.println(snowflake.nextId());
////        Thread.sleep(100);
//        System.out.println(snowflake.nextId());
////        Thread.sleep(100);
//        System.out.println(snowflake.nextId());
        SnowflakeIdWorker snowflakeIdWorker = SnowflakeIdWorker.getInstance();
        System.out.println("自定义："+snowflakeIdWorker.nextId());
//        SnowflakeIdWorker snowflakeIdWorker = SnowflakeIdWorker.getInstance();
        String idStr  = String.valueOf(snowflakeIdWorker.nextId());
        // 想要后面的15位 （为了防止第一位是0 只截取14位  然后前面拼接“1”）
        Long id = Long.parseLong("1" + idStr.substring(idStr.length()-14));
        System.out.println(id);
//        System.out.println("自定义："+snowflakeIdWorker.nextId());
//        System.out.println("自定义："+snowflakeIdWorker.nextId());
//        System.out.println("自定义："+snowflakeIdWorker.nextId());
//        System.out.println("自定义："+snowflakeIdWorker.nextId());
//        System.out.println("自定义："+snowflakeIdWorker.nextId());
//        System.out.println("自定义："+snowflakeIdWorker.nextId());
//        System.out.println("自定义："+snowflakeIdWorker.nextId());
//        System.out.println("自定义："+snowflakeIdWorker.nextId());

        Snowflake snowflake = new Snowflake(0,0);
        System.out.println("糊涂工具生成："+snowflake.nextId());
    }

}
