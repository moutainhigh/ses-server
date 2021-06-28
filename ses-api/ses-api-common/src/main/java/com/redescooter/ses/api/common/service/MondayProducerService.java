package com.redescooter.ses.api.common.service;

/**
 * @ClassName MondayRecordService
 * @Description
 * @Author Charles
 * @Date 2021/06/23 13:14
 */
public interface MondayProducerService {

    void save(Long userId, String scooterResult, String email, String telphone);
}
