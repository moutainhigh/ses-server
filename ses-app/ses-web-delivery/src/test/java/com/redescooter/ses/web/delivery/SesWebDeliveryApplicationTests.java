package com.redescooter.ses.web.delivery;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.delivery.DeliveryEventEnums;
import com.redescooter.ses.api.foundation.service.base.GenerateService;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.DateUtil;
import com.redescooter.ses.tool.utils.MapUtil;
import com.redescooter.ses.web.delivery.constant.SequenceName;
import com.redescooter.ses.web.delivery.dao.base.CorDeliveryMapper;
import com.redescooter.ses.web.delivery.dao.base.CorDeliveryTraceMapper;
import com.redescooter.ses.web.delivery.dm.CorDelivery;
import com.redescooter.ses.web.delivery.dm.CorDeliveryTrace;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SesWebDeliveryApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void test() {
        System.out.println(RandomUtils.nextInt(1000, 9999));
    }

    private String timePastTenSecond(String time, int count) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dt = DateUtil.parse(time, "yyyy-MM-dd HH:mm:ss");
        Calendar newTime = Calendar.getInstance();
        newTime.setTime(dt);
        newTime.add(Calendar.SECOND, count);//日期加10秒

        Date dt1 = newTime.getTime();
        String retval = sdf.format(dt1);
        return retval;
    }

    @Autowired
    private CorDeliveryMapper corDeliveryMapper;
    @Reference
    private IdAppService idAppService;
    @Reference
    private GenerateService generateService;
    @Autowired
    private CorDeliveryTraceMapper corDeliveryTraceMapper;

    @Test
    public void test2() {
        Long userId = 1115547L;
        Long tenantId = 1088170L;
        saveDelivery(userId, tenantId, "2020-1-12 10:10:10");
        saveDelivery(userId, tenantId, "2020-1-13 10:10:10");
        saveDelivery(userId, tenantId, "2020-1-14 10:10:10");
        saveDelivery(userId, tenantId, "2020-1-15 10:10:10");
        saveDelivery(userId, tenantId, "2020-1-16 10:10:10");
        saveDelivery(userId, tenantId, "2020-1-17 10:10:10");
    }

    private void saveDelivery(Long tenantId, Long userId, String time) {
        QueryWrapper<CorDelivery> corDeliveryQueryWrapper = new QueryWrapper<>();
        corDeliveryQueryWrapper.eq(CorDelivery.COL_DR, 0);
        List<CorDelivery> corDeliveryList = corDeliveryMapper.selectList(corDeliveryQueryWrapper);

        Long deliveryId = null;

        for (CorDelivery item : corDeliveryList) {
            deliveryId = item.getId();
            item.setId(idAppService.getId(SequenceName.COR_DELIVERY));
            item.setDelivererId(userId);
            item.setTenantId(tenantId);
            item.setOrderNo(generateService.getOrderNo());
            item.setLongitude(new BigDecimal(randomLonLat("Lon")));
            item.setLatitude(new BigDecimal(randomLonLat("Lng")));
            item.setGeohash(MapUtil.geoHash(item.getLongitude().toString(), item.getLatitude().toString()));
            item.setTimeoutExpectde("18");
            item.setTimeOut(DateUtil.parse(timePastTenSecond(time, 1000), "yyyy-MM-dd HH:mm:ss"));
            item.setEtd(DateUtil.parse(timePastTenSecond(time, 1000), "yyyy-MM-dd HH:mm:ss"));
            item.setAtd(DateUtil.parse(timePastTenSecond(time, 1000), "yyyy-MM-dd HH:mm:ss"));
            item.setEta(DateUtil.parse(timePastTenSecond(time, 2000), "yyyy-MM-dd HH:mm:ss"));
            item.setAta(DateUtil.parse(timePastTenSecond(time, 1500), "yyyy-MM-dd HH:mm:ss"));
            item.setCreatedTime(item.getEtd());
            item.setUpdatedTime(item.getEtd());
            // 日志记录
            QueryWrapper<CorDeliveryTrace> corDeliveryTraceQueryWrapper = new QueryWrapper<>();
            corDeliveryTraceQueryWrapper.eq(CorDeliveryTrace.COL_DELIVERY_ID, deliveryId);
            corDeliveryTraceQueryWrapper.eq(CorDeliveryTrace.COL_DR, 0);
            List<CorDeliveryTrace> deliveryTraceList = corDeliveryTraceMapper.selectList(corDeliveryTraceQueryWrapper);
            deliveryTraceList.forEach(trace -> {
                trace.setId(idAppService.getId(SequenceName.COR_DELIVERY_TRACE));
                trace.setDeliveryId(item.getId());

                if (StringUtils.equals(trace.getEvent(), DeliveryEventEnums.CREATE.getValue())) {
                    trace.setEventTime(item.getEtd());
                    trace.setCreatedTime(item.getEtd());
                }

                if (StringUtils.equals(trace.getEvent(), DeliveryEventEnums.START.getValue())) {
                    trace.setEventTime(item.getEtd());
                    trace.setCreatedTime(item.getEtd());
                }

                if (StringUtils.equals(trace.getEvent(), DeliveryEventEnums.CHANAGE.getValue())) {
                    trace.setEventTime(DateUtil.parse(timePastTenSecond(time, 3000), "yyyy-MM-dd HH:mm:ss"));
                    trace.setCreatedTime(DateUtil.parse(timePastTenSecond(time, 3000), "yyyy-MM-dd HH:mm:ss"));
                }

                if (StringUtils.equals(trace.getEvent(), DeliveryEventEnums.TIMEOUT.getValue())) {
                    trace.setEventTime(item.getEta());
                    trace.setCreatedTime(item.getEta());
                }
                if (StringUtils.equals(trace.getEvent(), DeliveryEventEnums.REJECT.getValue())) {
                    trace.setEventTime(item.getEtd());
                    trace.setCreatedTime(item.getEtd());
                }
                if (StringUtils.equals(trace.getEvent(), DeliveryEventEnums.CHANAGE.getValue())) {
                    trace.setEventTime(item.getEtd());
                    trace.setCreatedTime(item.getEtd());
                }
                if (StringUtils.equals(trace.getEvent(), DeliveryEventEnums.COMPLETED.getValue())) {
                    trace.setEventTime(item.getAta());
                    trace.setCreatedTime(item.getAta());
                }
                if (StringUtils.equals(trace.getEvent(), DeliveryEventEnums.TIMEOUT_COMPLETE.getValue())) {
                    trace.setEventTime(item.getAta());
                    trace.setCreatedTime(item.getAta());
                }
                trace.setCreatedTime(trace.getEventTime());
                trace.setUpdatedTime(trace.getEventTime());

                trace.setUserId(userId);
                trace.setTenantId(tenantId);

            });
            if (CollectionUtils.isNotEmpty(deliveryTraceList)) {
                corDeliveryTraceMapper.batchInsert(deliveryTraceList);
            }
        }
        if (CollectionUtils.isNotEmpty(corDeliveryList)) {
            corDeliveryMapper.batchInsert(corDeliveryList);
        }
    }

    private String randomLonLat(String type) {

        Double MinLon = 1.5152710000d;
        Double MaxLon = 4.0150030000d;
        Double MinLat = 47.4174040000d;
        Double MaxLat = 49.5093090000d;
        Random random = new Random();
        BigDecimal db = new BigDecimal(Math.random() * (MaxLon - MinLon) + MinLon);
        String lon = db.setScale(6, BigDecimal.ROUND_HALF_UP).toString();// 小数后6位
        db = new BigDecimal(Math.random() * (MaxLat - MinLat) + MinLat);
        String lat = db.setScale(6, BigDecimal.ROUND_HALF_UP).toString();
        if (type.equals("Lon")) {
            return lon;
        } else {
            return lat;
        }
    }
}
