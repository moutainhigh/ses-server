package com.redescooter.ses.mobile.rps.dao.inwhorder;

import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.mobile.rps.dm.OpeInWhouseOrder;
import com.redescooter.ses.mobile.rps.vo.inwhorder.InWhOrderDetailDTO;
import com.redescooter.ses.mobile.rps.vo.inwhorder.QueryInWhOrderParamDTO;
import com.redescooter.ses.mobile.rps.vo.inwhorder.QueryInWhOrderResultDTO;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.outbound.CountByOrderTypeParamDTO;

import java.util.List;

/**
 * 入库单 Mapper接口
 * @author assert
 * @date 2021/1/15 19:19
 */
public interface InWhOrderMapper {

    /**
     * 获取所有入库单类型数量
     * @param
     * @return java.util.List<com.redescooter.ses.api.common.vo.CountByStatusResult>
     * @author assert
     * @date 2021/1/15
    */
    List<CountByStatusResult> getInWarehouseOrderTypeCount();

    /**
     * 获取所有入库类型数量
     * @param paramDTO
     * @return java.util.List<com.redescooter.ses.api.common.vo.CountByStatusResult>
     * @author assert
     * @date 2021/1/15
    */
    List<CountByStatusResult> getInWarehouseTypeCount(CountByOrderTypeParamDTO paramDTO);

    /**
     * 获取入库单数量
     * @param paramDTO
     * @return int
     * @author assert
     * @date 2021/1/15
    */
    int countByInWhOrder(QueryInWhOrderParamDTO paramDTO);

    /**
     * 入库单列表查询
     * @param paramDTO
     * @return java.util.List<com.redescooter.ses.mobile.rps.vo.inwhorder.QueryInWhOrderResultDTO>
     * @author assert
     * @date 2021/1/15
    */
    List<QueryInWhOrderResultDTO> getInWarehouseOrderList(QueryInWhOrderParamDTO paramDTO);

    /**
     * 修改入库单信息
     * @param opeInWhouseOrder
     * @return int
     * @author assert
     * @date 2021/1/18
    */
    int updateInWhOrder(OpeInWhouseOrder opeInWhouseOrder);

    /**
     * 根据id查询入库单详情
     * @param id
     * @return com.redescooter.ses.mobile.rps.vo.inwhorder.InWhOrderDetailDTO
     * @author assert
     * @date 2021/1/18
    */
    InWhOrderDetailDTO getInWhOrderDetailById(Long id);

    /**
     * 根据id查询入库单信息
     * @param id
     * @return com.redescooter.ses.mobile.rps.dm.OpeInWhouseOrder
     * @author assert
     * @date 2021-01-21
     */
    OpeInWhouseOrder getInWhOrderById(Long id);

}
