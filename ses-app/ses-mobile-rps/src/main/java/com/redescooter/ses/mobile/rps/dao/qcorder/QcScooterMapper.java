package com.redescooter.ses.mobile.rps.dao.qcorder;

import com.redescooter.ses.mobile.rps.vo.qc.QcOrderProductDTO;
import com.redescooter.ses.mobile.rps.vo.qc.QcOrderProductDetailDTO;

import java.util.List;

/**
 * 质检单车辆信息 Mapper接口
 * @author assert
 * @date 2021/1/26 11:07
 */
public interface QcScooterMapper {

    /**
     * 根据qcId查询质检单车辆信息
     * @param qcId
     * @return java.util.List<com.redescooter.ses.mobile.rps.vo.qc.QcOrderProductDTO>
     * @author assert
     * @date 2021/1/26
    */
    List<QcOrderProductDTO> getQcScooterByQcId(Long qcId);

    /**
     * 根据id查询质检单车辆详情
     * @param id
     * @return com.redescooter.ses.mobile.rps.vo.qc.QcOrderProductDetailDTO
     * @author assert
     * @date 2021/1/26
    */
    QcOrderProductDetailDTO getQcScooterDetailById(Long id);

}
