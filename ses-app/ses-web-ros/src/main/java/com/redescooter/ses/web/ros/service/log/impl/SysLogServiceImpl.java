package com.redescooter.ses.web.ros.service.log.impl;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.service.log.SysLogService;
import com.redescooter.ses.web.ros.vo.log.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassNameSysLogServiceImpl
 * @Description
 * @Author Aleks
 * @Date2020/9/16 19:52
 * @Version V1.0
 **/
@Slf4j
@Service
public class SysLogServiceImpl implements SysLogService {


    @Override
    public PageResult<LogListResult> logList(LogListEnter enter) {
        List<LogListResult> list = new ArrayList<>();
        LogListResult result = new LogListResult();
        result.setId(0L);
        result.setCreatedTime(new Date());
        result.setLogContent("操作成功");
        result.setLoginIp("192.168.2.200");
        result.setOpUserCode("S000001");
        result.setOpUserName("Amy");
        result.setTimeConsum(30L);
        list.add(result);
        return PageResult.create(enter, 1, list);
    }

    @Override
    public GeneralResult logExport(LogExportEnter enter) {

        return new GeneralResult(enter.getRequestId());
    }

    @Override
    public List<LogCountResult> logCount(GeneralEnter enter) {
        List<LogCountResult> list = new ArrayList<>();
        LogCountResult result1 = new LogCountResult();
        result1.setType(1);
        result1.setNum(1);
        list.add(result1);
        LogCountResult result2 = new LogCountResult();
        result2.setType(2);
        result2.setNum(2);
        list.add(result2);
        LogCountResult result3 = new LogCountResult();
        result3.setType(3);
        result3.setNum(3);
        list.add(result3);
        return list;
    }

    @Override
    public LogDetailResult logDetail(IdEnter enter) {

        return new LogDetailResult();
    }
}
