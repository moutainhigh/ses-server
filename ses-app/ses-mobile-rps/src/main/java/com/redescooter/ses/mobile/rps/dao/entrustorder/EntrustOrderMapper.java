package com.redescooter.ses.mobile.rps.dao.entrustorder;

import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.mobile.rps.dm.OpeEntrustOrder;
import com.redescooter.ses.mobile.rps.vo.entrustorder.EntrustOrderDetailDTO;
import com.redescooter.ses.mobile.rps.vo.entrustorder.QueryEntrustOrderParamDTO;
import com.redescooter.ses.mobile.rps.vo.entrustorder.QueryEntrustOrderResultDTO;

import java.util.List;

/**
 * 委托单相关 Mapper接口
 * @author assert
 * @date 2020/12/30 18:19
 */
public interface EntrustOrderMapper {

    /**
     * 委托单类型数量统计
     * @param
     * @return java.util.Map<java.lang.Integer,java.lang.Integer>
     * @author assert
     * @date 2020/12/30
     */
    List<CountByStatusResult> getEntrustOrderTypeCount();

    /**
     * 委托单列表查询
     * @param paramDTO
     * @return com.redescooter.ses.api.common.vo.base.PageResult<com.redescooter.ses.mobile.rps.vo.entrustorder.QueryEntrustOrderResultDTO>
     * @author assert
     * @date 2021/1/4
     */
    List<QueryEntrustOrderResultDTO> getEntrustOrderList(QueryEntrustOrderParamDTO paramDTO);

    /**
     * 查询委托单数量
     * @param paramDTO
     * @return int
     * @author assert
     * @date 2021/1/4
    */
    int countByEntrustOrder(QueryEntrustOrderParamDTO paramDTO);

    /**
     * 根据id查询委托单详情
     * @param id
     * @return com.redescooter.ses.mobile.rps.vo.entrustorder.EntrustOrderDetailDTO
     * @author assert
     * @date 2021/1/4
    */
    EntrustOrderDetailDTO getEntrustOrderDetailById(Long id);
    
    /**
     * 修改委托单信息
     * @param opeEntrustOrder
     * @return int
     * @author assert
     * @date 2021-01-10
     */
    int updateEntrustOrder(OpeEntrustOrder opeEntrustOrder);

    /**
     * 根据id查询委托单信息
     * @param id
     * @return com.redescooter.ses.mobile.rps.dm.OpeEntrustOrder
     * @author assert
     * @date 2021/1/29
    */
    OpeEntrustOrder getEntrustOrderById(Long id);

}
