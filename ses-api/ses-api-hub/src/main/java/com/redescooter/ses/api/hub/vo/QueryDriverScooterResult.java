package com.redescooter.ses.api.hub.vo;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @ClassName:QueryDriverScooterResult
 * @description: QueryDriverScooterResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/01 19:14
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = true)
public class QueryDriverScooterResult extends GeneralResult {
    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "逻辑删除标识 0正常 1删除")
    private Integer dr;

    @ApiModelProperty(value = "租户ID")
    private Long tenantId;

    @ApiModelProperty(value = "司机ID")
    private Long userId;

    @ApiModelProperty(value = "车辆ID")
    private Long scooterId;

    @ApiModelProperty(value = "车辆分配开始时间")
    private Date beginTime;

    @ApiModelProperty(value = "车辆归还时间")
    private Date endTime;

    @ApiModelProperty(value = "状态 USED，FINSH，使用中、未使用")
    private String status;

    @ApiModelProperty(value = "行驶里程")
    private Double mileage;
}
