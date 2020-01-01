package com.redescooter.ses.api.mobile.b.vo;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @ClassName:SaveDriverRideStatEnter
 * @description: SaveDriverRideStatEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/30 16:53
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class SaveDeliveryStatEnter extends GeneralEnter {

    @ApiModelProperty(value = "耗时")
    private Long duration;

    @ApiModelProperty(value = "总公里数")
    private Double mileage;
    ;

    @ApiModelProperty(value = "业务类型")
    private String bizType;

    @ApiModelProperty(value = "业务id")
    private Long bizId;

    @ApiModelProperty(value = "交付人Id")
    private Long inputUserId;

    @ApiModelProperty(value = "交付人租户Id")
    private Long inputTenantId;

    @ApiModelProperty(value = "业务最后的更新时间")
    private Date lastUpdateTime;
}
