package com.redescooter.ses.mobile.rps.service.restproductionorder.invoice;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.invoice.InvoiceUpdateStatusEnter;

/**
 * @ClassName:InvoiceOrderService
 * @description: InvoiceOrderService
 * @author: Alex
 * @Version：1.3
 * @create: 2020/11/05 16:44 
 */
public interface InvoiceOrderService {
    /**
    * @Description
    * @Author: alex
    * @Date:   2020/11/5 4:55 下午
    * @Param:  enter
    * @Return: GeneralResult
    * @desc: 更新发货单状态
    */
    GeneralResult updateStatus(InvoiceUpdateStatusEnter enter);
}
