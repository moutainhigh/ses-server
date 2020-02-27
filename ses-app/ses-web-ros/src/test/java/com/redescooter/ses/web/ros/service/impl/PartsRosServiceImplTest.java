package com.redescooter.ses.web.ros.service.impl;

import java.math.BigDecimal;
import java.util.Date;


import com.redescooter.ses.api.common.enums.bom.BomTypeEnums;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dm.OpePartsType;
import com.redescooter.ses.web.ros.service.base.OpePartsTypeService;
import com.redescooter.ses.web.ros.vo.bom.parts.PartsTypeResult;
import org.apache.dubbo.config.annotation.Reference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PartsRosServiceImplTest {


    @Autowired
    private OpePartsTypeService partsTypeService;

    @Reference
    private IdAppService idAppService;


    @Test
    public void typeCount() {

        List<PartsTypeResult> resultList = new ArrayList<>();

        List<OpePartsType> list = partsTypeService.list();
        if (list.size() > 0) {
            list.forEach(type -> {
                PartsTypeResult result = new PartsTypeResult();
                result.setId(type.getId());
                result.setName(type.getName());
                result.setValue(type.getValue());
                resultList.add(result);
            });
        }

        System.out.println(resultList.toString());
    }


    @Test
    public void createType() {

        List<OpePartsType> list = new ArrayList<>();

        for (BomTypeEnums typeEnums : BomTypeEnums.values()) {
            OpePartsType inster = new OpePartsType();
            inster.setId(idAppService.getId(SequenceName.OPE_PARTS_TYPE));
            inster.setDr(0);
            inster.setTenantId(0L);
            inster.setUserId(0L);
            inster.setStatus("1");
            inster.setName(typeEnums.getCode());
            inster.setCode(typeEnums.getCode());
            inster.setValue(Integer.parseInt(typeEnums.getValue()));
            inster.setCreatedBy(0L);
            inster.setCreatedTime(new Date());
            inster.setUpdatedBy(0L);
            inster.setUpdatedTime(new Date());
            list.add(inster);
        }

        partsTypeService.saveBatch(list);

    }
}
