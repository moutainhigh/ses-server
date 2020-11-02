package com.redescooter.ses.mobile.rps.dao.restproductionorder.invoice;

import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.outbound.OutboundOrderEnter;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.outbound.OutboundOrderResult;

import java.util.List;

/**
 * @ClassName:InvoiceOrderSrviceMapper
 * @description: InvoiceOrderSrviceMapper
 * @author: Alex
 * @Version：1.3
 * @create: 2020/11/02 15:49 
 */

public interface InvoiceOrderSrviceMapper {
    /**
    * @Description
    * @Author: alex
    * @Date:   2020/11/2 3:51 下午
    * @Param:  enter
    * @Return: CountByStatusResult
    * @desc: 产品类型统计
    */
    List<CountByStatusResult> countByProductType(GeneralEnter enter);

    /**
    * @Description
    * @Author: alex
    * @Date:   2020/11/2 4:25 下午
    * @Param:  enter
    * @Return: int
    * @desc: 单据列表
    */
    int listCount(OutboundOrderEnter enter);
    /**
    * @Description
    * @Author: alex
    * @Date:   2020/11/2 4:26 下午
    * @Param:  enter
    * @Return: OutboundOrderResult
    * @desc: 列表
    */
    List<OutboundOrderResult> list(OutboundOrderEnter enter);
}
