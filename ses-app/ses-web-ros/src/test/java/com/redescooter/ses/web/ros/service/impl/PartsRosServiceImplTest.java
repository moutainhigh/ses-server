package com.redescooter.ses.web.ros.service.impl;

import com.redescooter.ses.api.common.enums.bom.BomCommonTypeEnums;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.foundation.service.base.CityBaseService;
import com.redescooter.ses.api.foundation.vo.common.CityByPageEnter;
import com.redescooter.ses.api.foundation.vo.common.CityResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.DateUtil;
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
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PartsRosServiceImplTest {


    @Autowired
    private OpePartsTypeService partsTypeService;

    @Reference
    private CityBaseService cityBaseService;

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

        for (BomCommonTypeEnums typeEnums : BomCommonTypeEnums.values()) {
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

    @Test
    public void City() {
        CityByPageEnter cityByPageEnter = new CityByPageEnter();
        cityByPageEnter.setPageSize(10000);
        PageResult<CityResult> cityResultPageResult = cityBaseService.queryCityByParameterPage(cityByPageEnter);

        HashSet<Integer> level = new HashSet<Integer>();

        List<CityResult> list = cityResultPageResult.getList();
        list.forEach(item -> {
            level.add(item.getLevel());
        });
        Iterator<Integer> iterator = level.iterator();
        Map[] country = new Map[level.size()];
        Object[] objects = level.toArray();

        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < level.size(); j++) {
                if (country != null) {
                }
            }
        }

    }

//        Object[] toArray = level.toArray();
//        for (int i = 0; i <toArray.length; i++) {
//            for (int j = 0; j < list.size(); j++) {
//               if (toArray[i]==list.get(j).getLevel()){
//
//                   System.out.println(list.get(j).toString());
//               }
//            }
//        }
//    }

    @Test
    public void Timeout() {
        if (DateUtil.timeComparison("2020-03-06 02:02:02", "2020-03-06 02:02:03")) {
            System.out.println("2020-03-06 02:02:03");
        }
    }
}
