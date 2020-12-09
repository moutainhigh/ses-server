package com.redescooter.ses.service.scooter.service.impl;

import com.redescooter.ses.api.scooter.service.*;
import com.redescooter.ses.api.scooter.vo.emqx.ScooterAllReportedDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;

/**
 * @author assert
 * @date 2020/11/23 14:57
 */
@Slf4j
@Service
public class ScooterAllReportedServiceImpl implements ScooterAllReportedService {

    @Reference
    private ScooterEcuService scooterEcuService;
    @Reference
    private ScooterBbiService scooterBbiService;
    @Reference
    private ScooterMcuService scooterMcuService;
    @Reference
    private ScooterService scooterService;
    @Resource
    private TransactionTemplate transactionTemplate;


    @Override
    public int insertScooterAllInfo(ScooterAllReportedDTO scooterAll) {
        transactionTemplate.execute(scooterAllStatus -> {
            try {
                // ECU仪表数据
                scooterEcuService.insertScooterEcuByEmqX(scooterAll.getEcu());
                // BBI、BMS、BatteryWare 电池相关数据
                scooterBbiService.insertScooterBbiByEmqX(scooterAll.getBbi());
                // MCU控制器数据
                scooterMcuService.insertScooterMcuByEmqX(scooterAll.getMcu());
                // LOCK车锁状态 ---- 已废弃,车辆锁状态直接从ecu中拿
//                scooterService.updateScooterStatusByJson(scooterAll.getLock());
            } catch (Exception e) {
                log.error("【车辆整车数据上报失败】----{}", ExceptionUtils.getStackTrace(e));
                scooterAllStatus.setRollbackOnly();
            }
            return 1;
        });
        return 1;
    }

}
