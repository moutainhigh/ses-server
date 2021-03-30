package com.redescooter.ses.api.foundation.service.scooter;

/**
 * @Description
 * @Author Chris
 * @Date 2021/3/26 11:57
 */
public interface AssignScooterService {

    /**
     * 根据email判断是否在pla_user表存在
     */
    Boolean getPlaUserIsExistByEmail(String email);

}
