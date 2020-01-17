package com.redescooter.ses.service.mobile.b;

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

//    @Reference
//    private DeliveryService deliveryService;
//
//    @Reference
//    private StatisticalDataService statisticalDataService;
//
//    @Test
//    public void list() {
//
//      System.out.println(StringUtils.isAllBlank(String.valueOf(null), String.valueOf(null)));
//    }
//
//    @Test
//    public void detail() {
//        IdEnter idEnter = new IdEnter();
//        idEnter.setUserId(1060938L);
//        idEnter.setTenantId(1060980L);
//        idEnter.setId(1067025L);
//        System.out.println(deliveryService.detail(idEnter));
//    }
//
//    @Test
//    public void start() {
//        StartEnter startEnter = new StartEnter();
//        startEnter.setUserId(1060938L);
//        startEnter.setTenantId(1060980L);
//        startEnter.setId(1067025L);
//        startEnter.setLat("48.8694328000");
//        startEnter.setLng("2.3302575000");
//        startEnter.setMileage("1000");
//        startEnter.setBluetoothCommunication(Boolean.FALSE);
//        System.out.println(deliveryService.start(startEnter));
//    }
//
//    @Test
//    public void complete() {
//        CompleteEnter completeEnter = new CompleteEnter();
//        completeEnter.setUserId(1060938L);
//        completeEnter.setTenantId(1060980L);
//        completeEnter.setId(1067025L);
//        completeEnter.setLat("48.8694328000");
//        completeEnter.setLng("2.3302575000");
//        completeEnter.setBluetoothCommunication(Boolean.FALSE);
//        System.out.println(deliveryService.complete(completeEnter));
//    }
//
//    @Test
//    public void refuse() {
//        RefuseEnter refuseEnter = new RefuseEnter();
//        refuseEnter.setUserId(1060938L);
//        refuseEnter.setTenantId(1060980L);
//        refuseEnter.setId(1067025L);
//        refuseEnter.setReason("客户取消");
//        System.out.println(deliveryService.refuse(refuseEnter));
//    }
//
//    @Test
//    public void saveDriverRideStat() {
//        SaveDeliveryStatEnter enter = SaveDeliveryStatEnter.builder()
//                .bizId(1067025L)
//                .bizType(BizType.DELIVERY.getValue())
//                .duration(10L)
//                .mileage(1000000d)
//                .build();
//        enter.setTenantId(0L);
//        enter.setUserId(1060938L);
//        List<SaveDeliveryStatEnter> saveDeliveryStatEnterList = new ArrayList<>();
//        saveDeliveryStatEnterList.add(enter);
//        statisticalDataService.saveDriverRideStat(saveDeliveryStatEnterList);
//    }
//
//    @Test
//    public void saveScooterRideStat() {
//        SaveDeliveryStatEnter enter = SaveDeliveryStatEnter.builder()
//                .bizId(1067025L)
//                .bizType(BizType.DELIVERY.getValue())
//                .duration(10L)
//                .mileage(1000000d)
//                .build();
//        enter.setTenantId(0L);
//        enter.setUserId(1060938L);
//        List<SaveDeliveryStatEnter> saveDeliveryStatEnterList = new ArrayList<>();
//        saveDeliveryStatEnterList.add(enter);
//        statisticalDataService.saveScooterRideStat(saveDeliveryStatEnterList);
//    }
//
//    @Autowired
//    private JedisCluster jedisCluster;
//
//    @Test
//    public void test() {
//        String json = "{\"systemId\":\"REDE_SAAS\",\"requestId\":\"9fa2764feb9e4fde8821ca2dff8a7029\",\"appId\":\"2\",\"name\":\"bill\",\"userId\":\"1069386\",\"email\":\"bill@redescooter.com\"," +
//                "\"verificationCode\":\"41014\"}";
//        Map<String, String> map = (Map) JSON.parse(json);
//
//        String key = "9fa2764feb9e4fde8821ca2dff8a7029";
//        jedisCluster.hmset(key, map);
//        jedisCluster.expire(key, 10);
//    }
}
