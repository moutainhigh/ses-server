package com.redescooter.ses.web.ros.verifyhandler;

import cn.afterturn.easypoi.excel.entity.result.ExcelVerifyHandlerResult;
import cn.afterturn.easypoi.handler.inter.IExcelVerifyHandler;
import com.redescooter.ses.web.ros.vo.sellsy.result.SellsyExcleData;
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
public class SellsyExcelVerifyHandlerImpl implements IExcelVerifyHandler<SellsyExcleData> {
    /**
     * 导入校验方法
     *
     * @param obj 当前对象
     * @return
     */
    @Override
    public ExcelVerifyHandlerResult verifyHandler(SellsyExcleData obj) {
        //SesStringUtils.objStringTrim(obj);
        StringBuilder builder = new StringBuilder();

        /*//校验发票号
        if (StringUtils.isEmpty(obj.getInvoice_num())){
            builder.append(SellsyExcleData.NUMÉRO + ",Invalid input, please check;");
            return new ExcelVerifyHandlerResult(false, builder.toString());
        }

        //校验父及子关系
        if (StringUtils.isEmpty(obj.getIsParent())){
            builder.append(SellsyExcleData.IS_PARENT + ",Invalid input, please check;");
            return new ExcelVerifyHandlerResult(false, builder.toString());
        }*/

        //剔除掉金额中间的空格
//        if (StringUtils.isNotBlank(obj.getUnitPrice())) {
//            obj.setUnitPrice(obj.getUnitPrice().replaceAll(" ", ""));
//        }
//
        if (StringUtils.equals(obj.getPayType(), "00/01/1900")) {
            obj.setPayTime(null);
        }
        if (StringUtils.isNotBlank(obj.getTva())) {
            obj.setTva(String.valueOf(Double.valueOf(obj.getTva()) * 100));
        }
//
//        if (StringUtils.isNotBlank(obj.getRemainingPayment())) {
//            obj.setRemainingPayment(obj.getRemainingPayment().replaceAll(" ", ""));
//        }
        return new ExcelVerifyHandlerResult(true, builder.toString());
    }

}
