package com.redescooter.ses.service.mobile.b;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.mobile.b.service.DeliveryService;
import com.redescooter.ses.api.mobile.b.vo.CompleteEnter;
import com.redescooter.ses.api.mobile.b.vo.RefuseEnter;
import com.redescooter.ses.api.mobile.b.vo.StartEnter;
import org.apache.dubbo.config.annotation.Reference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SesServiceMobileBApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Reference
    private DeliveryService deliveryService;

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
        startEnter.setLon("2.3302575000");
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
        completeEnter.setLon("2.3302575000");
        completeEnter.setMileage("1000");
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

}
