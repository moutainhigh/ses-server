package com.redescooter.ses.service.mobile.b;

import com.redescooter.ses.api.common.enums.base.BizType;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.mobile.b.service.DeliveryService;
import com.redescooter.ses.api.mobile.b.service.StatisticalDataService;
import com.redescooter.ses.api.mobile.b.vo.CompleteEnter;
import com.redescooter.ses.api.mobile.b.vo.RefuseEnter;
import com.redescooter.ses.api.mobile.b.vo.SaveDeliveryStatEnter;
import com.redescooter.ses.api.mobile.b.vo.StartEnter;
import org.apache.dubbo.config.annotation.Reference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SesServiceMobileBApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Reference
    private DeliveryService deliveryService;

    @Reference
    private StatisticalDataService statisticalDataService;

    @Test
    public void list() {
        GeneralEnter enter = new GeneralEnter();
        enter.setUserId(1060938L);
        enter.setTenantId(1060980L);
        System.out.println(deliveryService.list(enter));
    }

    @Test
    public void detail() {
        IdEnter idEnter = new IdEnter();
        idEnter.setUserId(1060938L);
        idEnter.setTenantId(1060980L);
        idEnter.setId(1067025L);
        System.out.println(deliveryService.detail(idEnter));
    }

    @Test
    public void start() {
        StartEnter startEnter = new StartEnter();
        startEnter.setUserId(1060938L);
        startEnter.setTenantId(1060980L);
        startEnter.setId(1067025L);
        startEnter.setLat("48.8694328000");
        startEnter.setLng("2.3302575000");
        startEnter.setMileage("1000");
        startEnter.setBluetoothCommunication(Boolean.FALSE);
        System.out.println(deliveryService.start(startEnter));
    }

    @Test
    public void complete() {
        CompleteEnter completeEnter = new CompleteEnter();
        completeEnter.setUserId(1060938L);
        completeEnter.setTenantId(1060980L);
        completeEnter.setId(1067025L);
        completeEnter.setLat("48.8694328000");
        completeEnter.setLng("2.3302575000");
        completeEnter.setBluetoothCommunication(Boolean.FALSE);
        System.out.println(deliveryService.complete(completeEnter));
    }

    @Test
    public void refuse() {
        RefuseEnter refuseEnter = new RefuseEnter();
        refuseEnter.setUserId(1060938L);
        refuseEnter.setTenantId(1060980L);
        refuseEnter.setId(1067025L);
        refuseEnter.setReason("客户取消");
        System.out.println(deliveryService.refuse(refuseEnter));
    }

    @Test
    public void saveDriverRideStat() {
        SaveDeliveryStatEnter enter = SaveDeliveryStatEnter.builder()
                .bizId(1067025L)
                .bizType(BizType.DELIVERY.getValue())
                .duration(10L)
                .mileage(1000000d)
                .build();
        enter.setTenantId(0L);
        enter.setUserId(1060938L);
        List<SaveDeliveryStatEnter> saveDeliveryStatEnterList = new ArrayList<>();
        saveDeliveryStatEnterList.add(enter);
        statisticalDataService.saveDriverRideStat(saveDeliveryStatEnterList);
    }

    @Test
    public void saveScooterRideStat() {
        SaveDeliveryStatEnter enter = SaveDeliveryStatEnter.builder()
                .bizId(1067025L)
                .bizType(BizType.DELIVERY.getValue())
                .duration(10L)
                .mileage(1000000d)
                .build();
        enter.setTenantId(0L);
        enter.setUserId(1060938L);
        List<SaveDeliveryStatEnter> saveDeliveryStatEnterList = new ArrayList<>();
        saveDeliveryStatEnterList.add(enter);
        statisticalDataService.saveScooterRideStat(saveDeliveryStatEnterList);
    }

}
