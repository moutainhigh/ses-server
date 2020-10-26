package com.redescooter.ses.mobile.rps.dao.printentry;

import com.redescooter.ses.mobile.rps.vo.printentry.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PrintEntryServiceMapper {
    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/23 15:05
     * @Param: enter
     * @Return: int
     * @desc: 来料质检
     */
    int meterialCount(@Param("enter") PrintEnteyEnter enter);

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/23 15:06
     * @Param: enter
     * @Return: PrintEntryResult
     * @desc: 来料质检列表
     */
    List<PrintEntryResult> meterialList(@Param("enter") PrintEnteyEnter enter);

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/23 15:06
     * @Param: enter
     * @Return: int
     * @desc: 采购入库
     */
    int purchasCount(PrintEnteyEnter enter);

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/23 15:07
     * @Param: enter
     * @Return: PrintEntryResult
     * @desc: 采购入库
     */
    List<PrintEntryResult> purchasList(PrintEnteyEnter enter);

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/23 15:47
     * @Param: enter
     * @Return: int
     * @desc: 订单部件列表
     */
    int orderCount(PrintEntryOrderEnter enter);

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/23 15:50
     * @Param: enter
     * @Return: PrintEntryOrderResult
     * @desc: 订单列表
     */
    List<PrintEntryOrderResult> orderList(PrintEntryOrderEnter enter);

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/23 15:50
     * @Param: List
     * @Return: PrintEntryOrderDetailResult
     * @desc: 订单详情
     */
    List<PrintEntryOrderDetailResult> orderPartDetail(List<Long> purchasIds);

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/10/26 10:26
     * @Return: PrintEntryOrderDetailResult
     * @desc: 部件质检记录
     */
    List<PrintEntryOrderDetailResult> partQcDetail();
}
