package com.redescooter.ses.service.scooter.dao;

import com.redescooter.ses.api.scooter.vo.emqx.ScooterBmsReportedDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author assert
 * @date 2020/11/23 16:52
 */
public interface ScooterBmsMapper {

    /**
     * 根据scooterNo查询车辆电池信息
     * @param scooterNo
     * @return java.util.List<java.lang.Integer>
     * @author assert
     * @date 2020/11/23
     */
    List<Integer> getScooterBmsWareNoByScooterNo(String scooterNo);

    /**
     * 批量新增车辆BMS电池信息
     * @param scooterBmsList
     * @return int
     * @author assert
     * @date 2020/11/23
     */
    int batchInsertScooterBms(@Param("scooterBmsList") List<ScooterBmsReportedDTO> scooterBmsList);

    /**
     * 批量修改车辆BMS电池信息
     * @param scooterBmsList
     * @return int
     * @author assert
     * @date 2020/11/23
     */
    int batchUpdateScooterBms(@Param("scooterBmsList") List<ScooterBmsReportedDTO> scooterBmsList,
                              @Param("scooterNo") String scooterNo);

}
