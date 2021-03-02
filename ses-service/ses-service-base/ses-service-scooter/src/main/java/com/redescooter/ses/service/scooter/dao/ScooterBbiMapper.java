package com.redescooter.ses.service.scooter.dao;

import com.redescooter.ses.api.scooter.vo.emqx.ScooterBbiReportedDTO;
import org.apache.ibatis.annotations.Param;

/**
 * @author assert
 * @date 2020/11/23 16:51
 */
public interface ScooterBbiMapper {

    /**
     * 根据scooterNo和batchNo查询车辆BBI信息
     * @param scooterNo, batchNo
     * @return com.redescooter.ses.api.scooter.vo.emqx.ScooterBbiReportedDTO
     * @author assert
     * @date 2020/11/20
     */
    ScooterBbiReportedDTO getScooterBbiByScooterNoAndBatchNo(@Param("scooterNo") String scooterNo, @Param("batchNo") Long batchNo);

    /**
     * 根据id修改车辆BBI信息
     * @param scooterBbi
     * @return int
     * @author assert
     * @date 2020/11/23
    */
    int updateScooterBbiById(ScooterBbiReportedDTO scooterBbi);

    /**
     * 新增车辆BBI信息
     * @param scooterBbi
     * @return int
     * @author assert
     * @date 2020/11/23
    */
    int insertScooterBbi(ScooterBbiReportedDTO scooterBbi);

}
