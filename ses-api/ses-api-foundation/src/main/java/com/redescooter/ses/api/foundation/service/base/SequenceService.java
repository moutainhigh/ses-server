package com.redescooter.ses.api.foundation.service.base;


import com.redescooter.ses.api.foundation.exception.SequenceException;

import java.util.List;


/**
 * @description: SequenceService
 * @author: Darren
 * @create: 2019/01/09 11:14
 */
public interface SequenceService {

    /**
     * 主键生成，此服务可以分布式部署
     *
     * @param name sequence名称
     * @return long类型的唯一id
     * @Author zhangzhanfeng
     */
    long get(String name) throws SequenceException;

    /**
     * 批量获取sequence
     *
     * @param name  sequence名称
     * @param batch 批次大小 范围：[1-10000]
     * @return 一批 ID
     * @throws SequenceException
     */
    List<Long> batchGet(String name, int batch) throws SequenceException;

}
