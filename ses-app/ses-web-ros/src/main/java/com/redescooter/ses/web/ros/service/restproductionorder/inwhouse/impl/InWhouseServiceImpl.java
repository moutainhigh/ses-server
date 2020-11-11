package com.redescooter.ses.web.ros.service.restproductionorder.inwhouse.impl;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.web.ros.dao.restproductionorder.InWhouseOrderServiceMapper;
import com.redescooter.ses.web.ros.service.restproductionorder.inwhouse.InWhouseService;
import com.redescooter.ses.web.ros.vo.restproductionorder.inwhouse.InWhouseListEnter;
import com.redescooter.ses.web.ros.vo.restproductionorder.inwhouse.InWhouseListResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.inwhouse.InWhouseSaveOrUpdateEnter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassNameInWhouseServiceImpl
 * @Description
 * @Author Aleks
 * @Date2020/11/11 10:20
 * @Version V1.0
 **/
@Service
public class InWhouseServiceImpl implements InWhouseService {

    @Autowired
    private InWhouseOrderServiceMapper inWhouseOrderServiceMapper;


    @Override
    public PageResult<InWhouseListResult> inWhouseList(InWhouseListEnter enter) {
        enter = SesStringUtils.objStringTrim(enter);
        int totalNum = inWhouseOrderServiceMapper.totalNum(enter);
        if (totalNum == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        List<InWhouseListResult> list = null;
        return PageResult.create(enter, totalNum, list);
    }


    @Override
    public GeneralResult inWhouseSave(InWhouseSaveOrUpdateEnter enter) {
        return new GeneralResult(enter.getRequestId());
    }


    @Override
    public GeneralResult inWhouseEdit(InWhouseSaveOrUpdateEnter enter) {
        return new GeneralResult(enter.getRequestId());
    }

    @Override
    public Map<String, Integer> listCount(GeneralEnter enter) {
        Map<String, Integer> map = new HashMap<>();
        Integer num1 = 0;
        Integer num2 = 0;
        Integer num3 = 0;
        map.put("1",num1);
        map.put("2",num2);
        map.put("3",num3);
        return map;
    }
}
