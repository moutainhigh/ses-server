package com.redescooter.ses.service.scooter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.dubbo.config.annotation.Reference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.redescooter.ses.api.common.enums.scooter.CommonEvent;
import com.redescooter.ses.api.common.vo.scooter.BaseScooterResult;
import com.redescooter.ses.api.common.vo.scooter.IotScooterEnter;
import com.redescooter.ses.api.scooter.service.ScooterIotService;
import com.redescooter.ses.api.scooter.service.ScooterService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SesServiceScooterApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Reference
	private ScooterService scooterService;
	@Reference
	private ScooterIotService scooterIotService;

	@Test
	public void queruScooterInfor(){
		List<Long> idEnterList=new ArrayList<>();
		idEnterList.add(1020945L);

		List<BaseScooterResult> results = scooterService.scooterInfor(idEnterList);
		System.out.println(results.toString());
	}

	@Test
	public void navigation() {
		IotScooterEnter iotScooterEnter=new IotScooterEnter();
		iotScooterEnter.setId(1020945L);
		iotScooterEnter.setEvent(CommonEvent.START.getValue());
		iotScooterEnter.setLatitude(new BigDecimal("34.4898561378"));
		iotScooterEnter.setLongitude(new BigDecimal("113.9004187600"));
		iotScooterEnter.setBluetoothCommunication(Boolean.TRUE);
		iotScooterEnter.setUserId(0L);
		iotScooterEnter.setTenantId(0L);

		// 开始 无蓝牙
//		scooterIotService.navigation(iotScooterEnter);

		// 结束无蓝牙
		iotScooterEnter.setEvent(CommonEvent.END.getValue());
		scooterIotService.navigation(iotScooterEnter);

	}
}
