package com.redescooter.ses.mobile.client.controller;

import com.redescooter.ses.api.common.enums.scooter.ScooterActionTypeEnums;
import com.redescooter.ses.api.common.vo.scooter.IotScooterEnter;
import com.redescooter.ses.api.scooter.service.ScooterIotService;
import com.redescooter.ses.mobile.client.SesMobileClientApplicationTests;
import org.apache.dubbo.config.annotation.Reference;
import org.junit.Test;

import java.math.BigDecimal;

public class ScooterControllerTest extends SesMobileClientApplicationTests {

    @Reference
    private ScooterIotService scooterIotService;


    @Test
    public void lock() {

        IotScooterEnter iotScooterEnter = new IotScooterEnter();
        iotScooterEnter.setLongitude(new BigDecimal("0"));
        iotScooterEnter.setLatitude(new BigDecimal("0"));
        //iotScooterEnter.setId();
        iotScooterEnter.setBluetoothCommunication(Boolean.TRUE);
        iotScooterEnter.setEvent(ScooterActionTypeEnums.LOCK.getValue());
        scooterIotService.lock(iotScooterEnter);
    }
}
