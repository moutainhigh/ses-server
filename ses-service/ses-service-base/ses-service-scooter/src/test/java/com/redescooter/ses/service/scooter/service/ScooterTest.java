package com.redescooter.ses.service.scooter.service;

import java.util.Date;


import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.redescooter.ses.service.scooter.constant.SequenceName;
import com.redescooter.ses.service.scooter.dao.base.ScoScooterMapper;
import com.redescooter.ses.service.scooter.dm.base.ScoScooter;
import com.redescooter.ses.starter.common.service.IdAppService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ScooterTest {

    @Autowired
    private IdAppService idAppService;

    @Autowired
    private ScoScooterMapper scoScooterMapper;

    @Test
    public void createBaseVehicle() {

        ScoScooter save = null;
        List<ScoScooter> saveList = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            save = new ScoScooter();
            save.setId(idAppService.getId(SequenceName.SCO_SCOOTER));
            save.setDr(0);
            save.setScooterNo(IdUtil.objectId());
            save.setPicture("https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg");
            save.setStatus("1");
            save.setTotalMileage(0L);
            save.setAvailableStatus("1");
            save.setBoxStatus("1");
            save.setModel("3");
            save.setLicensePlate(RandomUtil.randomString(2) + "-" + RandomUtil.randomString(2)+"-" + RandomUtil.randomString(2));
            save.setLicensePlatePicture("https://rede.oss-cn-shanghai.aliyuncs.com/1574231627614.jpeg");
            save.setLicensePlateTime(new Date());
            save.setScooterIdPicture("0");
            save.setInsureTime(new Date());
            save.setInsurance("0");
            save.setRevision(0);
            save.setCreatedBy(0L);
            save.setCreatedTime(new Date());
            save.setUpdatedBy(0L);
            save.setUpdatedTime(new Date());
            saveList.add(save);
        }

        scoScooterMapper.batchInsert(saveList);
    }

    @Test
    public void test01() {
        for (int i = 0; i < 100; i++) {
            System.out.println(RandomUtil.randomString(2) + "-" + RandomUtil.randomString(2)+"-" + RandomUtil.randomString(2));
        }

    }
}