package com.redescooter.ses.web.ros.service.log.impl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.tool.utils.DateUtil;
import com.redescooter.ses.web.ros.dao.log.LogServiceMapper;
import com.redescooter.ses.web.ros.dm.OpeSysLog;
import com.redescooter.ses.web.ros.service.base.OpeSysLogService;
import com.redescooter.ses.web.ros.service.log.SysLogService;
import com.redescooter.ses.web.ros.vo.log.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Put;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.swing.text.Style;
import java.util.*;
import java.util.stream.Collectors;

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


    @Autowired
    private OpeSysLogService opeSysLogService;

    @Autowired
    private LogServiceMapper logServiceMapper;

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
        int totalRows = logServiceMapper.totalRows(enter);
        if (totalRows == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        list = logServiceMapper.logList(enter);
        return PageResult.create(enter, 1, list);
    }

    @Override
    public GeneralResult logExport(String id, HttpServletResponse response) {
        // 传的id，多个用，隔开的
        String[] ids = id.split(",");
        QueryWrapper<OpeSysLog> qw = new QueryWrapper<>();
        qw.in(OpeSysLog.COL_ID,ids);
        List<OpeSysLog> logs = opeSysLogService.list(qw);
        if(CollectionUtils.isNotEmpty(logs)){
            List<LogExport> list = new ArrayList<>();
            for (OpeSysLog sysLog : logs) {
                LogExport logExport = new LogExport();
                logExport.setOpUserCode(sysLog.getOpUserCode());
                logExport.setOpUserName(sysLog.getOpUserName());
                logExport.setLogContent(sysLog.getIfSuccess()==null?"fail":(sysLog.getIfSuccess()==1?"success":"fail"));
                logExport.setLoginIp(sysLog.getLoginIp());
                logExport.setCreatedTime(DateUtil.format(sysLog.getCreatedTime(),null));
                logExport.setOpModul(sysLog.getOpModul());
                logExport.setTimeConsum(sysLog.getTimeConsum()==null?"0":sysLog.getTimeConsum()+"");
                list.add(logExport);
            }
            try {
                // 设置响应输出的头类型
                response.setHeader("content-Type", "application/vnd.ms-excel");
                // 下载文件的默认名称
                response.setHeader("Content-Disposition", "attachment;filename=" + System.currentTimeMillis() + ".xls");
                // =========easypoi部分
                ExportParams exportParams = new ExportParams();
                exportParams.setSheetName("log");
                // exportParams.setDataHanlder(null);//和导入一样可以设置一个handler来处理特殊数据
                Workbook workbook = ExcelExportUtil.exportExcel(exportParams, LogExport.class, list);
                workbook.write(response.getOutputStream());
            } catch (Exception e) {
                System.out.println("+++++++++++++++++++");
            }
        }
        return new GeneralResult();
    }


    @Override
    public Map<String,Integer> logCount(GeneralEnter enter) {
        Map<String,Integer> map = new HashMap<>();
        // 1 2 3分别对应登陆、操作、错误日志，
        Integer num1 = 0;
        Integer num2 = 0;
        Integer num3 = 0;
        List<OpeSysLog> logs = opeSysLogService.list();
        num1 = logs.stream().filter(o->o.getLogType() == 1).collect(Collectors.toList()).size();
        num2 = logs.stream().filter(o->o.getLogType() == 2).collect(Collectors.toList()).size();
        num3 = logs.stream().filter(o->o.getIfSuccess() == 0).collect(Collectors.toList()).size();
        map.put("1",num1);
        map.put("2",num2);
        map.put("3",num3);
        return map;
    }


    @Override
    public LogDetailResult logDetail(IdEnter enter) {
        LogDetailResult detailResult = new LogDetailResult();
        OpeSysLog log = opeSysLogService.getById(enter.getId());
        BeanUtils.copyProperties(log,detailResult);
        return detailResult;
    }
}
