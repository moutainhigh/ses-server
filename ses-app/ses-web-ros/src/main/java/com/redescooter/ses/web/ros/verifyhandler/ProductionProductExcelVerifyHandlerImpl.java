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
     * @param rosParseExcelData
     *            当前对象
     * @return
     */
    @Override
    public ExcelVerifyHandlerResult verifyHandler(RosParseExcelData rosParseExcelData) {
        StringBuilder builder = new StringBuilder();
        if (Strings.isNullOrEmpty(rosParseExcelData.getPartsNo())) {
            builder.append("PARTS N°is empty;");
            return new ExcelVerifyHandlerResult(false, builder.toString());
        }
        if (Strings.isNullOrEmpty(rosParseExcelData.getChineseName())) {
            builder.append("Chinese_Name is empty;");
            return new ExcelVerifyHandlerResult(false, builder.toString());
        }
        if (Strings.isNullOrEmpty(rosParseExcelData.getEnglishName())) {
            builder.append("English_Name is empty;");
            return new ExcelVerifyHandlerResult(false, builder.toString());
        }
        if (Strings.isNullOrEmpty(rosParseExcelData.getSec())) {
            builder.append("SEC is empty;");
            return new ExcelVerifyHandlerResult(false, builder.toString());
        }
        if (Strings.isNullOrEmpty(rosParseExcelData.getQuantity())) {
            builder.append("Quantity is empty;");
            return new ExcelVerifyHandlerResult(false, builder.toString());
        } else {
            if (Integer.valueOf(rosParseExcelData.getQuantity()) < 1) {
                builder.append("Quantity , Must be greater than 1;");
                return new ExcelVerifyHandlerResult(false, builder.toString());
            }
        }
        return new ExcelVerifyHandlerResult(true, builder.toString());
    }
}
