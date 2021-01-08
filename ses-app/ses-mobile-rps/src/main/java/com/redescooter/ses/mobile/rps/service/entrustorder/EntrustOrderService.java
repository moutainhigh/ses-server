package com.redescooter.ses.mobile.rps.service.entrustorder;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.mobile.rps.vo.entrustorder.*;

import java.util.Map;

/**
 * 委托单业务接口
 * @author assert
 * @date 2020/12/30 18:11
 */
public interface EntrustOrderService {

    /**
     * 委托单类型数量统计
     * @param enter
     * @return java.util.Map<java.lang.Integer,java.lang.Integer>
     * @author assert
     * @date 2020/12/30
    */
    Map<Integer, Integer> getEntrustOrderTypeCount(GeneralEnter enter);

    /**
     * 委托单列表查询
     * @param paramDTO
     * @return com.redescooter.ses.api.common.vo.base.PageResult<com.redescooter.ses.mobile.rps.vo.entrustorder.QueryEntrustOrderResultDTO>
     * @author assert
     * @date 2021/1/4
    */
    PageResult<QueryEntrustOrderResultDTO> getEntrustOrderList(QueryEntrustOrderParamDTO paramDTO);

    /**
     * 根据id查询委托单详情
     * @param enter
     * @return com.redescooter.ses.mobile.rps.vo.entrustorder.EntrustOrderDetailDTO
     * @author assert
     * @date 2021/1/4
    */
    EntrustOrderDetailDTO getEntrustOrderDetailById(IdEnter enter);

    /**
     * 委托单发货
     * @param enter
     * @return com.redescooter.ses.api.common.vo.base.IdEnter
     * @author assert
     * @date 2021/1/4
    */
    GeneralResult entrustOrderDeliver(IdEnter enter);

    /**
     * 修改部件实际发货数量
     * @param paramDTO
     * @return com.redescooter.ses.api.common.vo.base.GeneralResult
     * @author assert
     * @date 2021/1/4
    */
    GeneralResult updatePartActualDeliveryQty(UpdatePartActualDeliveryQtyParamDTO paramDTO);

    /**
     * 保存委托单产品发货数量信息
     * @param paramDTO
     * @return com.redescooter.ses.api.common.vo.base.GeneralResult
     * @author assert
     * @date 2021/1/7
    */
    GeneralResult saveDeliverInfo(SaveProductDeliverInfoParamDTO paramDTO);

}
