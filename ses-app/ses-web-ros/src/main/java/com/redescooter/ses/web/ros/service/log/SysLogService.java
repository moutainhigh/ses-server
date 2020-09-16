package com.redescooter.ses.web.ros.service.log;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.vo.log.*;

import java.util.List;

/**
 * @ClassNameSysLogService
 * @Description
 * @Author kyle
 * @Date2020/9/16 19:51
 * @Version V1.0
 **/
public interface SysLogService {


    PageResult<LogListResult> logList(LogListEnter enter);


    GeneralResult logExport(LogExportEnter enter);


    List<LogCountResult> logCount(GeneralEnter enter);


    LogDetailResult logDetail(IdEnter enter);

}
