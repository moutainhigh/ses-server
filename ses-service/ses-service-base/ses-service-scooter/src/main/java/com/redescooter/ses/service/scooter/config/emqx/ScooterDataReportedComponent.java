package com.redescooter.ses.service.scooter.config.emqx;

import com.alibaba.fastjson.JSONObject;
import com.redescooter.ses.api.scooter.exception.ScooterException;
import com.redescooter.ses.api.scooter.service.ScooterAllReportedService;
import com.redescooter.ses.api.scooter.service.ScooterBbiService;
import com.redescooter.ses.api.scooter.service.ScooterEcuService;
import com.redescooter.ses.api.scooter.service.ScooterMcuService;
import com.redescooter.ses.api.scooter.service.ScooterService;
import com.redescooter.ses.api.scooter.vo.emqx.ScooterAllReportedDTO;
import com.redescooter.ses.api.scooter.vo.emqx.ScooterBbiReportedDTO;
import com.redescooter.ses.api.scooter.vo.emqx.ScooterEcuDTO;
import com.redescooter.ses.api.scooter.vo.emqx.ScooterMcuReportedDTO;
import com.redescooter.ses.service.scooter.config.RequestKeyProperties;
import com.redescooter.ses.service.scooter.exception.ExceptionCodeEnums;
import com.redescooter.ses.starter.emqx.constants.EmqXTopicConstant;
import com.redescooter.ses.tool.crypt.RsaUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 车辆设备数据上报组件
 *
 * @author assert
 * @date 2020/11/19 17:48
 */
@Slf4j
@Component
public class ScooterDataReportedComponent {

    @DubboReference
    private ScooterEcuService scooterEcuService;
    @DubboReference
    private ScooterBbiService scooterBbiService;
    @DubboReference
    private ScooterMcuService scooterMcuService;
    @DubboReference
    private ScooterAllReportedService scooterAllReportedService;
    @DubboReference
    private ScooterService scooterService;

    @Autowired
    private RequestKeyProperties requestKeyProperties;

    /**
     * 车辆数据保存到数据库
     *
     * @param data
     * @param topic
     */
    public void insertScooterData(String data, String topic) {

        // 数据上报(安卓给后端),解密
        String decryptData = "";
        if (StringUtils.isNotBlank(data)) {
            try {
                decryptData = RsaUtils.decrypt(data, requestKeyProperties.getPrivateKey());
            } catch (Exception e) {
                throw new ScooterException(ExceptionCodeEnums.DATA_DECRYPT_WRONG.getCode(), ExceptionCodeEnums.DATA_DECRYPT_WRONG.getMessage());
            }
        }

        log.info("上报主题：【{}】", topic);
        log.info("上报数据：{}", decryptData);

        /**
         * 根据Topic进行业务分发
         */
        if (EmqXTopicConstant.SCOOTER_ECU_REPORTED_TOPIC.equals(topic)) {
            // ecu
            ScooterEcuDTO scooterEcu = JSONObject.parseObject(decryptData, ScooterEcuDTO.class);
            scooterEcuService.insertScooterEcuByEmqX(scooterEcu);
        } else if (EmqXTopicConstant.SCOOTER_BBI_REPORTED_TOPIC.equals(topic)) {
            // bbi
            ScooterBbiReportedDTO scooterReportedBbi = JSONObject.parseObject(decryptData, ScooterBbiReportedDTO.class);
            scooterBbiService.insertScooterBbiByEmqX(scooterReportedBbi);
        } else if (EmqXTopicConstant.SCOOTER_MCU_REPORTED_TOPIC.equals(topic)) {
            // mcu
            ScooterMcuReportedDTO scooterReportedMcu = JSONObject.parseObject(decryptData, ScooterMcuReportedDTO.class);
            scooterMcuService.insertScooterMcuByEmqX(scooterReportedMcu);
        } else if (EmqXTopicConstant.SCOOTER_ALL_REPORTED_TOPIC.equals(topic)) {
            // all
            ScooterAllReportedDTO scooterAll = JSONObject.parseObject(decryptData, ScooterAllReportedDTO.class);
            scooterAllReportedService.insertScooterAllInfo(scooterAll);
        }
//        else if (EmqXTopicConstant.SCOOTER_LOCK_REPORTED_TOPIC.equals(topic)) {
//             //lock 已废弃,车辆锁状态直接从ecu中拿
//            ScooterLockReportedDTO scooterLock = JSONObject.parseObject(decryptData, ScooterLockReportedDTO.class);
//            scooterService.updateScooterStatusByJson(scooterLock);
//        }
    }

}
