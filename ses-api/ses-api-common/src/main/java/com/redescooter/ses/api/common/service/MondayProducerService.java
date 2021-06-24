package com.redescooter.ses.api.common.service;

import org.springframework.scheduling.annotation.Async;

/**
 * @ClassName MondayRecordService
 * @Description
 * @Author Charles
 * @Date 2021/06/23 13:14
 */
public interface MondayProducerService {
    @Async
    void save(Long userId, String scooterResult, String email, String telphone);
}
