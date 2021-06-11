package com.redescooter.ses.web.ros.verifyhandler;

import cn.afterturn.easypoi.excel.entity.result.ExcelVerifyHandlerResult;
import cn.afterturn.easypoi.handler.inter.IExcelVerifyHandler;
import com.google.common.base.Strings;
import com.redescooter.ses.web.ros.vo.codebase.RsnImportData;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName RsnExcelVerifyHandlerImpl
 * @Description
 * @Author Charles
 * @Date 2021/06/11 14:29
 */
@Slf4j
public class RsnExcelVerifyHandlerImpl implements IExcelVerifyHandler<RsnImportData> {
    @Override
    public ExcelVerifyHandlerResult verifyHandler(RsnImportData rsnImportData) {
        StringBuilder builder = new StringBuilder();
        if (Strings.isNullOrEmpty(rsnImportData.getRsn())) {
            builder.append("RSN is empty;");
            return new ExcelVerifyHandlerResult(false, builder.toString());
        }
        return new ExcelVerifyHandlerResult(true, builder.toString());
    }
}
