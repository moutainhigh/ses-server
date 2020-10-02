package com.redescooter.ses.web.ros.vo.log;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * @ClassNameLogExport
 * @Description
 * @Author Aleks
 * @Date2020/9/17 17:40
 * @Version V1.0
 **/
@Data
public class LogExport {

    @Excel(name = "USER CODE")
    private String opUserCode = "";

    @Excel(name = "USER NAME")
    private String opUserName = "";

    @Excel(name = "MODUL")
    private String opModul;

    @Excel(name = "OPRATION TIME")
    private String createdTime;

    @Excel(name = "IP")
    private String loginIp = "";

    @Excel(name = "TIME COMSUM")
    private String timeConsum;

    @Excel(name = "RESULT")
    private String logContent;


}
