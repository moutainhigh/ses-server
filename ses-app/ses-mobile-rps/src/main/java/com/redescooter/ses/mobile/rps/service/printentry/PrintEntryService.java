package com.redescooter.ses.mobile.rps.service.printentry;

import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.mobile.rps.vo.printentry.PrintEnteyEnter;
import com.redescooter.ses.mobile.rps.vo.printentry.PrintEntryOrderEnter;
import com.redescooter.ses.mobile.rps.vo.printentry.PrintEntryOrderResult;
import com.redescooter.ses.mobile.rps.vo.printentry.PrintEntryResult;

public interface PrintEntryService {
    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/23 14:33
     * @Param: enter
     * @Return: PrintEntryResult
     * @desc: 条码打印单据列表
     */
    PageResult<PrintEntryResult> list(PrintEnteyEnter enter);

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/23 14:35
     * @Param: enter
     * @Return: PrintEntryOrderResult
     * @desc: 订单列表
     */
    PageResult<PrintEntryOrderResult> orderDetailList(PrintEntryOrderEnter enter);
}
