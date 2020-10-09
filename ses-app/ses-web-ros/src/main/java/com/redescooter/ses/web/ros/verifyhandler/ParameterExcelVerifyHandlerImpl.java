package com.redescooter.ses.web.ros.verifyhandler;

import cn.afterturn.easypoi.excel.entity.result.ExcelVerifyHandlerResult;
import cn.afterturn.easypoi.handler.inter.IExcelVerifyHandler;
import com.redescooter.ses.web.ros.vo.setting.ImportParameterExcleData;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 18/9/2019 2:42 下午
 * @ClassName: OrdersExcelVerifyHandlerImpl
 * @Function: TODO
 */
@Slf4j
public class ParameterExcelVerifyHandlerImpl implements IExcelVerifyHandler<ImportParameterExcleData> {
    /**
     * 导入校验方法
     *
     * @param obj 当前对象
     * @return
     */
    @Override
    public ExcelVerifyHandlerResult verifyHandler(ImportParameterExcleData obj) {
        StringBuilder builder = new StringBuilder();
//        if (StringUtils.isEmpty(obj.getPartsN())) {
//            builder.append("Parts N ,This is  not must null;");
//            return new ExcelVerifyHandlerResult(false, builder.toString());
//        }
//        if (StringUtils.isEmpty(obj.getEsc())) {
//            builder.append("ESC,This is  not must null;");
//            return new ExcelVerifyHandlerResult(false, builder.toString());
//        }
//        if (StringUtils.isEmpty(obj.getType())) {
//            builder.append("Type,This is  not must null;");
//            return new ExcelVerifyHandlerResult(false, builder.toString());
//        }
//        if (StringUtils.isEmpty(BomCommonTypeEnums.checkCode(obj.getType()))) {
//            builder.append("Type,Invalid input, please check;");
//            return new ExcelVerifyHandlerResult(false, builder.toString());
//        }
//
//        if (StringUtils.isEmpty(BomSnClassEnums.checkCode(obj.getSnClass()))) {
//            builder.append("SN CLASS,Invalid input, please check;");
//            return new ExcelVerifyHandlerResult(false, builder.toString());
//        }
//        if (StringUtils.isEmpty(ESCUtils.checkESC(obj.getEsc()))) {
//            builder.append("ESC,Invalid input, please check;");
//            return new ExcelVerifyHandlerResult(false, builder.toString());
//        }
        return new ExcelVerifyHandlerResult(true, builder.toString());
    }
}
