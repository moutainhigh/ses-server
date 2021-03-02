package com.redescooter.ses.service.scooter.dao;

import com.redescooter.ses.api.scooter.vo.emqx.BatteryWareDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author assert
 * @date 2020/11/23 16:52
 */
public interface ScooterBbiBatteryWareMapper {

    /**
     * 批量更新电池仓位信息
     * @param batteryWares
     * @return int
     * @author assert
     * @date 2020/11/20
     */
    int batchUpdateBatteryWare(@Param("batteryWares") List<BatteryWareDTO> batteryWares);

    /**
     * 批量新增电池仓位信息
     * @param batteryWares
     * @return int
     * @author assert
     * @date 2020/11/23
     */
    int batchInsertBatteryWare(@Param("batteryWares") List<BatteryWareDTO> batteryWares);

}
