package com.redescooter.ses.mobile.rps.service.inwhorder;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.mobile.rps.vo.inwhorder.InWhOrderDetailDTO;
import com.redescooter.ses.mobile.rps.vo.inwhorder.QueryInWhOrderParamDTO;
import com.redescooter.ses.mobile.rps.vo.inwhorder.QueryInWhOrderResultDTO;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.outbound.CountByOrderTypeParamDTO;

import java.util.Map;

/**
 * 入库单相关业务接口
 * @author assert
 * @date 2021/1/15 18:09
 */
public interface InWhOrderService {

    /**
     * 入库单类型数量统计
     * @param enter
     * @return java.util.Map<java.lang.Integer,java.lang.Integer>
     * @author assert
     * @date 2021/1/15
    */
    Map<Integer, Integer> getInWarehouseOrderTypeCount(GeneralEnter enter);

    /**
     * 入库类型数量统计
     * @param paramDTO
     * @return java.util.Map<java.lang.Integer,java.lang.Integer>
     * @author assert
     * @date 2021/1/15
    */
    Map<Integer, Integer> getInWarehouseTypeCount(CountByOrderTypeParamDTO paramDTO);

    /**
     * 入库单列表查询
     * @param paramDTO
     * @return com.redescooter.ses.api.common.vo.base.PageResult<com.redescooter.ses.mobile.rps.vo.inwhorder.QueryInWhOrderResultDTO>
     * @author assert
     * @date 2021/1/15
    */
    PageResult<QueryInWhOrderResultDTO> getInWarehouseOrderList(QueryInWhOrderParamDTO paramDTO);

    /**
     * 入库单开始质检
     * @param enter
     * @return com.redescooter.ses.api.common.vo.base.GeneralResult
     * @author assert
     * @date 2021/1/15
    */
    GeneralResult startQc(IdEnter enter);

    /**
     * 根据id查询入库单详情
     * @param enter
     * @return com.redescooter.ses.mobile.rps.vo.inwhorder.InWhOrderDetailDTO
     * @author assert
     * @date 2021/1/15
    */
    InWhOrderDetailDTO getInWarehouseOrderDetailById(IdEnter enter);

}
