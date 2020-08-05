package com.redescooter.ses.service.foundation.service.impl;

import com.redescooter.ses.api.foundation.exception.SequenceException;
import com.redescooter.ses.api.foundation.service.base.SequenceService;
import com.redescooter.ses.service.foundation.constant.SequenceName;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class SequenceServiceImplTest {

    @Autowired
    private SequenceService sequenceService;

    @Test
    public void gitID() throws SequenceException {

        long l = sequenceService.get(SequenceName.PLA_CITY);

        System.out.println(l);
    }

}