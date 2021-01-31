package com.redescooter.ses.mobile.rps.dao.combinorder;

import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.mobile.rps.dm.OpeCombinOrder;
import com.redescooter.ses.mobile.rps.vo.combinorder.CombinationOrderDetailDTO;
import com.redescooter.ses.mobile.rps.vo.combinorder.QueryCombinationOrderParamDTO;
import com.redescooter.ses.mobile.rps.vo.combinorder.QueryCombinationOrderResultDTO;

import java.util.List;

/**
 * 组装单 Mapper接口
 * @author assert
 * @date 2021-01-21
 */
public interface CombinationOrderMapper {

    /**
     * 组装单类型数量统计
     * @param
     * @return java.util.List<com.redescooter.ses.api.common.vo.CountByStatusResult>
     * @author assert
     * @date 2021/1/27
    */
    List<CountByStatusResult> getCombinationOrderTypeCount();

    /**
     * 组装单数量统计
     * @param paramDTO
     * @return int
     * @author assert
     * @date 2021/1/27
    */
    int countByCombinationOrder(QueryCombinationOrderParamDTO paramDTO);

    /**
     * 组装单列表查询
     * @param paramDTO
     * @return java.util.List<com.redescooter.ses.mobile.rps.vo.combinorder.QueryCombinationOrderResultDTO>
     * @author assert
     * @date 2021/1/27
    */
    List<QueryCombinationOrderResultDTO> getCombinationOrderList(QueryCombinationOrderParamDTO paramDTO);

    /**
     * 根据id查询组装单详情
     * @param id
     * @return com.redescooter.ses.mobile.rps.vo.combinorder.CombinationOrderDetailDTO
     * @author assert
     * @date 2021/1/27
    */
    CombinationOrderDetailDTO getCombinationOrderDetailById(Long id);

    /**
     * 修改组装单信息
     * @param opeCombinOrder
     * @return int
     * @author assert
     * @date 2021/1/27
    */
    int updateCombinationOrder(OpeCombinOrder opeCombinOrder);

    /**
     * 根据id查询组装单信息
     * @param id
     * @return com.redescooter.ses.mobile.rps.dm.OpeCombinOrder
     * @author assert
     * @date 2021/1/27
    */
    OpeCombinOrder getCombinationOrderById(Long id);

    /**
     * 获取当天所有组装单id
     * @param
     * @return java.util.List<java.lang.Long>
     * @author assert
     * @date 2021/1/31
    */
    List<Long> getToDayCombinationId();

}
