package com.redescooter.ses.web.ros.service.log;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.vo.log.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @ClassNameSysLogService
 * @Description
 * @Author kyle
 * @Date2020/9/16 19:51
 * @Version V1.0
 **/
public interface SysLogService {


    PageResult<LogListResult> logList(LogListEnter enter);


    GeneralResult logExport(String id, HttpServletResponse response);


    Map<String,Integer> logCount(GeneralEnter enter);


    LogDetailResult logDetail(IdEnter enter);

}
