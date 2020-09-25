package com.redescooter.ses.web.ros.verifyhandler;

import cn.afterturn.easypoi.excel.entity.result.ExcelVerifyHandlerResult;
import cn.afterturn.easypoi.handler.inter.IExcelVerifyHandler;
import com.redescooter.ses.web.ros.vo.restproduct.RosParseExcelData;

/**
 * @ClassNameRosExcelParse
 * @Description
 * @Author Aleks
 * @Date2020/9/25 20:56
 * @Version V1.0
 **/
public class RosExcelParse implements IExcelVerifyHandler<RosParseExcelData> {
    @Override
    public ExcelVerifyHandlerResult verifyHandler(RosParseExcelData rosParseExcelData) {
       return new ExcelVerifyHandlerResult(true, "");
    }
}
