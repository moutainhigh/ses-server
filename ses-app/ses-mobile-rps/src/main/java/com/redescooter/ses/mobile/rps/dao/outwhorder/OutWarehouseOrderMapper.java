package com.redescooter.ses.mobile.rps.dao.outwhorder;

import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.mobile.rps.dm.OpeOutWhouseOrder;
import com.redescooter.ses.mobile.rps.vo.outwhorder.*;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.outbound.CountByOrderTypeParamDTO;

import java.util.List;

/**
 * 出库单相关 Mapper接口
 * @author assert
 * @date 2021/1/4 16:23
 */
public interface OutWarehouseOrderMapper {

    /**
     * 查询所有出库单类型数量
     * @param
     * @return java.util.List<com.redescooter.ses.api.common.vo.CountByStatusResult>
     * @author assert
     * @date 2021/1/4
    */
    List<CountByStatusResult> getOutWarehouseOrderTypeCount();

    /**
     * 查询所有出库类型数量
     * @param paramDTO
     * @return java.util.List<com.redescooter.ses.api.common.vo.CountByStatusResult>
     * @author assert
     * @date 2021/1/4
    */
    List<CountByStatusResult> getOutWarehouseTypeCount(CountByOrderTypeParamDTO paramDTO);

    /**
     * 查询出库单数量
     * @param paramDTO
     * @return int
     * @author assert
     * @date 2021/1/4
    */
    int countByOutWarehouseOrder(QueryOutWarehouseOrderParamDTO paramDTO);

    /**
     * 出库单列表查询
     * @param paramDTO
     * @return java.util.List<com.redescooter.ses.mobile.rps.vo.outwhorder.QueryOutWarehouseOrderResultDTO>
     * @author assert
     * @date 2021/1/4
    */
    List<QueryOutWarehouseOrderResultDTO> getOutWarehouseOrderList(QueryOutWarehouseOrderParamDTO paramDTO);

    /**
     * 根据id查询出库单详情
     * @param id
     * @return com.redescooter.ses.mobile.rps.vo.outwhorder.OutWarehouseOrderDetailDTO
     * @author assert
     * @date 2021/1/4
     */
    OutWarehouseOrderDetailDTO getOutWarehouseOrderDetailById(Long id);

    /**
     * 修改出库单信息
     * @param outWhouseOrder
     * @return int
     * @author assert
     * @date 2021/1/4
    */
    int updateOutWarehouseOrder(OpeOutWhouseOrder outWhouseOrder);

    /**
     * 根据id查询出库单已出库总数
     * @param id
     * @return int
     * @author assert
     * @date 2021/1/12
    */
    int getOutWhOrderAlreadyOutWhQtyById(Long id);

}
