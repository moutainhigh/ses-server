package com.redescooter.ses.web.ros.dao.log;

import com.redescooter.ses.web.ros.vo.log.LogListEnter;
import com.redescooter.ses.web.ros.vo.log.LogListResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassNameLogServiceMapper
 * @Description
 * @Author Aleks
 * @Date2020/9/17 18:59
 * @Version V1.0
 **/
public interface LogServiceMapper {


    int totalRows(@Param("enter") LogListEnter enter);

    List<LogListResult> logList(@Param("enter") LogListEnter enter);


}
