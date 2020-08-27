package com.redescooter.ses.web.ros.service.sellsy.impl;

import com.alibaba.fastjson.JSON;
import com.redescooter.ses.web.ros.constant.SellsyMethodConstant;
import com.redescooter.ses.web.ros.enums.sellsy.SellsyMethodTypeEnums;
import com.redescooter.ses.web.ros.service.sellsy.SellsyService;
import com.redescooter.ses.web.ros.service.sellsy.SellsyStaffService;
import com.redescooter.ses.web.ros.vo.sellsy.enter.SellsyExecutionEnter;
import com.redescooter.ses.web.ros.vo.sellsy.enter.SellsyIdEnter;
import com.redescooter.ses.web.ros.vo.sellsy.result.SellsyGeneralResult;
import com.redescooter.ses.web.ros.vo.sellsy.result.staff.SellsyStaffResult;
import lombok.extern.log4j.Log4j2;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Log4j2
@Service
public class SellsyStaffServiceImpl implements SellsyStaffService {

    @Autowired
    private SellsyService sellsyService;

    /**
     * 员工列表
     * 
     * @return
     */
    @Override
    public List<SellsyStaffResult> queryStaffList() {

        SellsyExecutionEnter sellsyExecutionEnter =
            SellsyExecutionEnter.builder().SellsyMethodType(SellsyMethodTypeEnums.QUERY.getValue())
                .method(SellsyMethodConstant.Accountdatas_GetRateCategories).params(null).build();

        SellsyGeneralResult sellsyGeneralResult = sellsyService.sellsyExecution(sellsyExecutionEnter);

        return sellsyService.jsonArrayFormatting(sellsyGeneralResult, new SellsyStaffResult());
    }

    /**
     * 查询指定员工
     * 
     * @param enter
     * @return
     */
    @Override
    public SellsyStaffResult queryStaffOne(SellsyIdEnter enter) {
        SellsyExecutionEnter sellsyExecutionEnter =
            SellsyExecutionEnter.builder().SellsyMethodType(SellsyMethodTypeEnums.QUERY.getValue())
                .method(SellsyMethodConstant.Accountdatas_GetRateCategories).params(null).build();

        SellsyGeneralResult sellsyGeneralResult = sellsyService.sellsyExecution(sellsyExecutionEnter);

        return JSON.parseObject(sellsyGeneralResult.getResult().toString(), SellsyStaffResult.class);
    }
}
