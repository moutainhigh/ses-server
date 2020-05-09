package com.redescooter.ses.api.hub.vo;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import io.swagger.annotations.*;

/**
 * @ClassName:SaveScooterEnter
 * @description: SaveScooterEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/26 11:50
 */
@ApiModel(value = "", description = "")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class HubSaveScooterEnter extends GeneralEnter {

    @ApiModelProperty(value = "车辆Id")
    private Long scooterId;

    @ApiModelProperty(value = "型号")
    private String model;

    @ApiModelProperty(value = "经度")
    private BigDecimal longitude;

    @ApiModelProperty(value = "维度")
    private BigDecimal latitude;

    @ApiModelProperty(value = "车牌")
    private String licensePlate;

    @ApiModelProperty(value = "车牌图片")
    private String licensePlatePicture;

    @ApiModelProperty(value = "状态")
    private String status;
}
