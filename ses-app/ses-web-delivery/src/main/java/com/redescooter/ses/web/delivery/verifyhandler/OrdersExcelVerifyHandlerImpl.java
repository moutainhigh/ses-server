package com.redescooter.ses.web.delivery.verifyhandler;

import cn.afterturn.easypoi.excel.entity.result.ExcelVerifyHandlerResult;
import cn.afterturn.easypoi.handler.inter.IExcelVerifyHandler;
import com.redescooter.ses.web.delivery.vo.excel.ExpressOrderExcleData;
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
public class OrdersExcelVerifyHandlerImpl implements IExcelVerifyHandler<ExpressOrderExcleData> {
    /**
     * 导入校验方法
     *
     * @param obj 当前对象
     * @return
     */
    @Override
    public ExcelVerifyHandlerResult verifyHandler(ExpressOrderExcleData obj) {

        StringBuilder builder = new StringBuilder();
        if (StringUtils.isEmpty(obj.getCustomerReference())) {
            builder.append("Customer Reference,This is  not must null;");
            return new ExcelVerifyHandlerResult(false, builder.toString());
        }
        if (StringUtils.isEmpty(obj.getSenderName())) {
            builder.append("Sender Name,This is  not must null;");
            return new ExcelVerifyHandlerResult(false, builder.toString());
        }
        if (StringUtils.isEmpty(obj.getSenderCompany())) {
            builder.append("Sender Company,This is  not must null;");
            return new ExcelVerifyHandlerResult(false, builder.toString());
        }

        if (StringUtils.isEmpty(obj.getSenderMail())) {
            builder.append("Sender Mail,This is  not must null;");
            return new ExcelVerifyHandlerResult(false, builder.toString());
        }

        if (StringUtils.isEmpty(obj.getSenderPhone())) {
            builder.append("Sender Phone,This is  not must null;");
            return new ExcelVerifyHandlerResult(false, builder.toString());
        }

        if (StringUtils.isEmpty(obj.getSenderAddress())) {
            builder.append("Sender Address,This is  not must null;");
            return new ExcelVerifyHandlerResult(false, builder.toString());
        }

        if (StringUtils.isEmpty(obj.getSenderCity())) {
            builder.append("Sender City,This is  not must null;");
            return new ExcelVerifyHandlerResult(false, builder.toString());
        }

        if (StringUtils.isEmpty(obj.getSenderPostcode())) {
            builder.append("Sender Postcode,This is  not must null;");
            return new ExcelVerifyHandlerResult(false, builder.toString());
        }

        if (StringUtils.isEmpty(obj.getRecipientMail())) {
            builder.append("Recipient Mail,This is  not must null;");
            return new ExcelVerifyHandlerResult(false, builder.toString());
        }

        if (StringUtils.isEmpty(obj.getRecipientPhone())) {
            builder.append("Recipient Phone,This is  not must null;");
            return new ExcelVerifyHandlerResult(false, builder.toString());
        }

        if (StringUtils.isEmpty(obj.getRecipientAddress())) {
            builder.append("Recipient Address,This is  not must null;");
            return new ExcelVerifyHandlerResult(false, builder.toString());
        }

        if (StringUtils.isEmpty(obj.getRecipientCity())) {
            builder.append("Recipient City,This is  not must null;");
            return new ExcelVerifyHandlerResult(false, builder.toString());
        }

        if (StringUtils.isEmpty(obj.getRecipientPostcode())) {
            builder.append("Recipient Postcode,This is  not must null;");
            return new ExcelVerifyHandlerResult(false, builder.toString());
        }
        return new ExcelVerifyHandlerResult(true, builder.toString());
    }
}
