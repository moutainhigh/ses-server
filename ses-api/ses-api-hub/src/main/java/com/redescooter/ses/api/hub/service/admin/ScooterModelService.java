package com.redescooter.ses.api.hub.service.admin;

import com.redescooter.ses.api.hub.vo.admin.AdmScooter;

/**
 * @Description
 * @Author Chris
 * @Date 2021/5/13 14:31
 */
public interface ScooterModelService {

    /**
     * 根据平板序列号查询车辆信息
     */
    AdmScooter getScooterBySn(String rsn);

    /**
     * 新增车辆
     */
    void insertScooter(AdmScooter scooter);

    /**
     * 根据id查询详情
     */
    AdmScooter getScooterById(Long id);




}
