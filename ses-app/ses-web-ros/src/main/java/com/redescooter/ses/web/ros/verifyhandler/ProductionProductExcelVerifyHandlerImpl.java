package com.redescooter.ses.web.ros.verifyhandler;

import cn.afterturn.easypoi.excel.entity.result.ExcelVerifyHandlerResult;
import cn.afterturn.easypoi.handler.inter.IExcelVerifyHandler;
import com.google.common.base.Strings;
import com.redescooter.ses.web.ros.vo.restproduct.RosParseExcelData;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 18/9/2019 2:42 下午
 * @ClassName: OrdersExcelVerifyHandlerImpl
 * @Function: TODO
 */
@Slf4j
public class ProductionProductExcelVerifyHandlerImpl implements IExcelVerifyHandler<RosParseExcelData> {
    /**
     * 导入校验方法
     *
     * @param obj
     *            当前对象
     * @return
     */
    @Override
    public ExcelVerifyHandlerResult verifyHandler(RosParseExcelData rosParseExcelData) {
        StringBuilder builder = new StringBuilder();
        if (Strings.isNullOrEmpty(rosParseExcelData.getPartsNo())) {
            builder.append("PARTS_NO ,This is  not must null;");
            return new ExcelVerifyHandlerResult(false, builder.toString());
        }
        if (Strings.isNullOrEmpty(rosParseExcelData.getChineseName())) {
            builder.append("Chinese_Name ,This is  not must null;");
            return new ExcelVerifyHandlerResult(false, builder.toString());
        }
        if (Strings.isNullOrEmpty(rosParseExcelData.getEnglishName())) {
            builder.append("English_Name ,This is  not must null;");
            return new ExcelVerifyHandlerResult(false, builder.toString());
        }
        if (Strings.isNullOrEmpty(rosParseExcelData.getSec())) {
            builder.append("SEC ,This is  not must null;");
            return new ExcelVerifyHandlerResult(false, builder.toString());
        }
        if (Strings.isNullOrEmpty(rosParseExcelData.getQuantity())) {
            builder.append("Quantity ,This is  not must null;");
            return new ExcelVerifyHandlerResult(false, builder.toString());
        }
        return new ExcelVerifyHandlerResult(true, builder.toString());
    }
}
