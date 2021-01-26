package com.redescooter.ses.mobile.rps.dao.qcorder;

import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.mobile.rps.dm.OpeQcOrder;
import com.redescooter.ses.mobile.rps.vo.qc.QcOrderDetailDTO;
import com.redescooter.ses.mobile.rps.vo.qc.QueryQcOrderParamDTO;
import com.redescooter.ses.mobile.rps.vo.qc.QueryQcOrderResultDTO;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.outbound.CountByOrderTypeParamDTO;

import java.util.List;

/**
 * 质检单 Mapper接口
 * @author assert
 * @date 2021/1/25 18:01
 */
public interface QcOrderMapper {

    /**
     * 质检单据类型数量统计
     * @param
     * @return java.util.List<com.redescooter.ses.api.common.vo.CountByStatusResult>
     * @author assert
     * @date 2021/1/25
    */
    List<CountByStatusResult> getQcOrderTypeCount();

    /**
     * 质检类型数量统计
     * @param paramDTO
     * @return java.util.List<com.redescooter.ses.api.common.vo.CountByStatusResult>
     * @author assert
     * @date 2021/1/25
    */
    List<CountByStatusResult> getQcTypeCount(CountByOrderTypeParamDTO paramDTO);

    /**
     * 质检单数量统计
     * @param paramDTO
     * @return int
     * @author assert
     * @date 2021/1/25
    */
    int countByQcOrder(QueryQcOrderParamDTO paramDTO);

    /**
     * 质检单列表查询
     * @param paramDTO
     * @return java.util.List<com.redescooter.ses.mobile.rps.vo.qc.QueryQcOrderResultDTO>
     * @author assert
     * @date 2021/1/25
    */
    List<QueryQcOrderResultDTO> getQcOrderList(QueryQcOrderParamDTO paramDTO);

    /**
     * 根据id查询质检单详情
     * @param id
     * @return com.redescooter.ses.mobile.rps.vo.qc.QcOrderDetailDTO
     * @author assert
     * @date 2021/1/26
    */
    QcOrderDetailDTO getQcOrderDetailById(Long id);

    /**
     * 修改质检单信息
     * @param opeQcOrder
     * @return int
     * @author assert
     * @date 2021/1/26
    */
    int updateQcOrder(OpeQcOrder opeQcOrder);

}
