package com.redescooter.ses.web.delivery.vo.mobile;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:MobileListResult
 * @description: MobileListResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/13 18:50
 */
@ApiModel(value = "车辆列表", description = "车辆列表")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class MobileResult extends GeneralResult {
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "车辆状态")
    private String status;

    @ApiModelProperty(value = "拍照")
    private String licensePlate;

    @ApiModelProperty(value = "车辆图片")
    private String mobilePicture;

    @ApiModelProperty(value = "电量")
    private Integer battery;

    @ApiModelProperty(value = "公里数")
    private String mileage;

    @ApiModelProperty(value = "未读")
    private String nextMaintenanceKm;

    @ApiModelProperty(value = "型号")
    private String model;

    @ApiModelProperty(value = "节省的co2")
    private String co2;

    @ApiModelProperty(value = "节省的钱")
    private String money;

    @ApiModelProperty(value = "司机Id")
    private Long driverId;

    @ApiModelProperty(value = "司机名字")
    private String driverFirstName;

    @ApiModelProperty(value = "司机名字")
    private String driverLastName;

}
