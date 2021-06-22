package com.redescooter.ses.web.ros.verifyhandler;

import cn.afterturn.easypoi.excel.entity.result.ExcelVerifyHandlerResult;
import cn.afterturn.easypoi.handler.inter.IExcelVerifyHandler;
import com.redescooter.ses.web.ros.vo.bom.sales.CustomerImportResult;
import com.redescooter.ses.web.ros.vo.sales.ImportCustomerExcleData;
import com.redescooter.ses.web.ros.vo.setting.ImportParameterExcleData;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 18/9/2019 2:42 下午
 * @ClassName: OrdersExcelVerifyHandlerImpl
 * @Function: TODO
 */
@Slf4j
public class CustomerExcelVerifyHandlerImpl implements IExcelVerifyHandler<ImportCustomerExcleData> {

    @Override
    public ExcelVerifyHandlerResult verifyHandler(ImportCustomerExcleData obj) {
        StringBuilder builder = new StringBuilder();
        if (StringUtils.isEmpty(obj.getFirstName())) {
            builder.append("Group Name ,This is  not must null;");
            return new ExcelVerifyHandlerResult(false, builder.toString());
        }
        if (StringUtils.isEmpty(obj.getLastName())) {
            builder.append("Key,This is  not must null;");
            return new ExcelVerifyHandlerResult(false, builder.toString());
        }
        if (StringUtils.isEmpty(obj.getAreaCode())) {
            builder.append("Value,This is  not must null;");
            return new ExcelVerifyHandlerResult(false, builder.toString());
        }
        if (StringUtils.isEmpty(obj.getEmail())) {
            builder.append("ParameterName,This is  not must null;");
            return new ExcelVerifyHandlerResult(false, builder.toString());
        }

        if (obj.getPhone() == null) {
            builder.append("Enable,This is  not must null;");
            return new ExcelVerifyHandlerResult(false, builder.toString());
        }
        return new ExcelVerifyHandlerResult(true, builder.toString());
    }
}
