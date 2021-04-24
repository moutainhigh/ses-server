package com.redescooter.ses.web.ros.service.assign.impl;

import com.google.common.collect.Lists;
import com.redescooter.ses.web.ros.SesWebRosApplicationTests;
import com.redescooter.ses.web.ros.enums.assign.ProductTypeEnum;
import com.redescooter.ses.web.ros.service.assign.ToBeAssignService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class ToBeAssignServiceImplTest extends SesWebRosApplicationTests {

    @Autowired
    private ToBeAssignService toBeAssignService;

    @Test
    void showTest() {

        //Integer[] array = {1, 2};
        List<String> list = Lists.newArrayList();
        for (int i = 0; i < 400; i++) {
            //int index = (int) (Math.random() * array.length);
            Integer seat = 2;
            String s = toBeAssignService.show(Long.valueOf("102104071540736"), ProductTypeEnum.E50.getMsg(), seat, i + 1);
            list.add(s);
        }
        log.info(list.toString());
    }

}