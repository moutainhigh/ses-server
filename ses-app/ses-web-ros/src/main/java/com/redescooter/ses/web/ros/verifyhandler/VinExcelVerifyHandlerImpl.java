package com.redescooter.ses.web.ros.verifyhandler;

import cn.afterturn.easypoi.excel.entity.result.ExcelVerifyHandlerResult;
import cn.afterturn.easypoi.handler.inter.IExcelVerifyHandler;
import com.google.common.base.Strings;
import com.redescooter.ses.web.ros.vo.codebase.VinImportData;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName VinExcelVerifyHandlerImpl
 * @Description
 * @Author Charles
 * @Date 2021/06/11 16:26
 */
@Slf4j
public class VinExcelVerifyHandlerImpl implements IExcelVerifyHandler<VinImportData> {
    @Override
    public ExcelVerifyHandlerResult verifyHandler(VinImportData vinImportData) {
        StringBuilder builder = new StringBuilder();
        if (Strings.isNullOrEmpty(vinImportData.getVin())) {
            builder.append("VIN is empty;");
            return new ExcelVerifyHandlerResult(false, builder.toString());
        }
        if (null == vinImportData.getSeatNumber()) {
            builder.append("座位数 is empty;");
            return new ExcelVerifyHandlerResult(false, builder.toString());
        }
        return new ExcelVerifyHandlerResult(true, builder.toString());
    }
}
