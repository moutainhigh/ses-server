package com.redescooter.ses.web.ros.service.log.impl;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.service.log.SysLogService;
import com.redescooter.ses.web.ros.vo.log.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

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
    public Map<String,Integer> logCount(GeneralEnter enter) {
        Map<String,Integer> map = new HashMap<>();
        map.put("1",1);
        map.put("2",2);
        map.put("3",3);
        return map;
    }

    @Override
    public LogDetailResult logDetail(IdEnter enter) {
        LogDetailResult detailResult = new LogDetailResult();
        detailResult.setId(0L);
        detailResult.setIfSuccess(1);
        detailResult.setExpMsg("1111");
        detailResult.setLoginIp("192.168.2.200");
        detailResult.setOpModul("部门新增");
        detailResult.setOpUserDeptName("部门管理");
        detailResult.setRequestAddress("/sys/sss/sss");
        detailResult.setOpUserName("Amy");
        detailResult.setRequestParam("xsvfz cxcDX");
        detailResult.setResponseParam("chdchfahcac");
        return detailResult;
    }
}
