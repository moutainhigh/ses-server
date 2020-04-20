package com.redescooter.ses.mobile.rps.service.purchasinwh.impl;

import com.alibaba.fastjson.JSON;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.mobile.rps.service.purchasinwh.PurchasPutStroageService;
import com.redescooter.ses.mobile.rps.vo.purchasinwh.NotIdEnter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class PurchasPutStorageServiceImplTest {

    @Autowired
    private PurchasPutStroageService purchasPutStroageService;

    @Test
    public void notIdPartsSucceedResult() {

        NotIdEnter enter = new NotIdEnter();
        enter.setId(78789l);
        enter.setInWaitWhQty(12);


        GeneralResult result = purchasPutStroageService.notIdInWh(enter);

        log.info(JSON.toJSONString(result));

    }


}
