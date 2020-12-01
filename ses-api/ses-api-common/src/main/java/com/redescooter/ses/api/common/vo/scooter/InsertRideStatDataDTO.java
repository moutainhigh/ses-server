package com.redescooter.ses.api.common.vo.scooter;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 新增车辆骑行数据 DTO
 * @author assert
 * @date 2020/11/25 10:49
 */
@Data
public class InsertRideStatDataDTO extends GeneralEnter {

    /**
     * 距离,单位m
     */
    private BigDecimal mileage;

    /**
     * 耗时,单位s
     */
    private Long duration;

    /**
     * 业务类型 1订单 2快递配送大订单 3快递配送小订单 4司机 5车辆 6维修
     */
    private String bizType;

    /**
     * 业务Id
     */
    private Long bizId;

}
