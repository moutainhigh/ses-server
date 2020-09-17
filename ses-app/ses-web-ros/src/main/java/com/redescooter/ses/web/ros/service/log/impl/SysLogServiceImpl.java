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

        return PageResult.createZeroRowResult(enter);
    }

    @Override
    public GeneralResult logExport(LogExportEnter enter) {

        return new GeneralResult(enter.getRequestId());
    }

    @Override
    public List<LogCountResult> logCount(GeneralEnter enter) {
        return new ArrayList<>();
    }

    @Override
    public LogDetailResult logDetail(IdEnter enter) {

        return new LogDetailResult();
    }
}
