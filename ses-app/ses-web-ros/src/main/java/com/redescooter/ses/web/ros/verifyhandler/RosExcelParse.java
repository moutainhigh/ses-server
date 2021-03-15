package com.redescooter.ses.web.ros.verifyhandler;

import cn.afterturn.easypoi.excel.entity.result.ExcelVerifyHandlerResult;
import cn.afterturn.easypoi.handler.inter.IExcelVerifyHandler;
import com.google.common.base.Strings;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
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
        StringBuilder builder = new StringBuilder();
        if (Strings.isNullOrEmpty(rosParseExcelData.getLevel())) {
            builder.append("level is empty;");
            return new ExcelVerifyHandlerResult(false, builder.toString());
        }
        if (Strings.isNullOrEmpty(rosParseExcelData.getPartsNo())) {
            builder.append("PARTS N°is empty;");
            throw new SesWebRosException(ExceptionCodeEnums.FILE_TEMPLATE_IS_INVALID.getCode(), ExceptionCodeEnums.FILE_TEMPLATE_IS_INVALID.getMessage());
        }
        if (Strings.isNullOrEmpty(rosParseExcelData.getChineseName())) {
            builder.append("Chinese_Name is empty;");
            return new ExcelVerifyHandlerResult(false, builder.toString());
        }
        if (Strings.isNullOrEmpty(rosParseExcelData.getEnglishName())) {
            builder.append("English_Name is empty;");
            return new ExcelVerifyHandlerResult(false, builder.toString());
        }
        if (Strings.isNullOrEmpty(rosParseExcelData.getEcnNumber())) {
            builder.append("ECN_Number is empty;");
            return new ExcelVerifyHandlerResult(false, builder.toString());
        }
        if (Strings.isNullOrEmpty(rosParseExcelData.getSec())) {
            builder.append("SEC is empty;");
            return new ExcelVerifyHandlerResult(false, builder.toString());
        }
        if (Strings.isNullOrEmpty(rosParseExcelData.getSellClass())) {
            builder.append("Sell_Class is empty;");
            return new ExcelVerifyHandlerResult(false, builder.toString());
        }
        if (Strings.isNullOrEmpty(rosParseExcelData.getType())) {
            builder.append("TYPE is empty;");
            return new ExcelVerifyHandlerResult(false, builder.toString());
        }
        if (Strings.isNullOrEmpty(rosParseExcelData.getDrawingSize())) {
            builder.append("Drawing_Size is empty;");
            return new ExcelVerifyHandlerResult(false, builder.toString());
        }
        if (Strings.isNullOrEmpty(rosParseExcelData.getWeight())) {
            builder.append("Weight is empty;");
            return new ExcelVerifyHandlerResult(false, builder.toString());
        }
        if (Strings.isNullOrEmpty(rosParseExcelData.getQuantity())) {
            builder.append("Quantity is empty;");
            return new ExcelVerifyHandlerResult(false, builder.toString());
        }
        return new ExcelVerifyHandlerResult(true, builder.toString());
    }
}
