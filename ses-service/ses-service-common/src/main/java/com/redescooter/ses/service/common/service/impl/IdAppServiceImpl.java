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

    @Reference
    private SequenceService sequenceService;

    @Override
    public long getId(String tableName) {
        try {
//            log.info("采用雪花算法");
            // 糊涂工具自带的雪花算法
//            Snowflake snowflake = new Snowflake(0,0);
//            return snowflake.nextId();
            SnowflakeIdWorker snowflakeIdWorker = SnowflakeIdWorker.getInstance();
            return snowflakeIdWorker.nextId();
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

        Snowflake snowflake = new Snowflake(0,0);
        System.out.println("糊涂工具生成："+snowflake.nextId());
    }

}
