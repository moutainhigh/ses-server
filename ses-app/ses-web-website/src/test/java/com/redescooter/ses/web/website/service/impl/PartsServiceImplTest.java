package com.redescooter.ses.web.website.service.impl;

import com.redescooter.ses.web.website.SesWebsiteApplicationTests;
import com.redescooter.ses.web.website.enums.CommonStatusEnums;
import com.redescooter.ses.web.website.enums.PartsProcurementSourceEnums;
import com.redescooter.ses.web.website.enums.PartsTypeEnums;
import com.redescooter.ses.web.website.service.PartsService;
import com.redescooter.ses.web.website.vo.parts.AddPartsEnter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

public class PartsServiceImplTest extends SesWebsiteApplicationTests {

    @Autowired
    private PartsService partsService;

    @Test
    void addParts() {

        AddPartsEnter enter = new AddPartsEnter();
        enter.setPartsType(PartsTypeEnums.BATTERY.getValue());
        enter.setPartsNumber("3770018097152");
        enter.setCnName("电池");
        enter.setFrName("Batt Model E H Capa");
        enter.setEnName("Batt Model E H Capa");
        enter.setSpecs("");
        enter.setPrice(new BigDecimal("1290"));
        enter.setUserId(0L);
        partsService.addParts(enter);

    }
}