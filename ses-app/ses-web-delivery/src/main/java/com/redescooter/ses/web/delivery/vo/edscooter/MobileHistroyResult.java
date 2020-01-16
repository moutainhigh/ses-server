package com.redescooter.ses.web.delivery.vo.edscooter;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @ClassName:MobileHistroyResult
 * @description: MobileHistroyResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/14 14:50
 */
@ApiModel(value = "车辆分配维修历史", description = "车辆分配维修历史")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class MobileHistroyResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "司机Id")
    private Long driverId;

    @ApiModelProperty(value = "司机姓名")
    private String driverFirstName;

    @ApiModelProperty(value = "司机姓名")
    private String driverLastName;

    @ApiModelProperty(value = "车辆Id")
    private Long scooterId;

    @ApiModelProperty(value = "电量")
    private Integer battery;

    @ApiModelProperty(value = "公里数 最小单位")
    private String mileage;

    @ApiModelProperty(value = "分配时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private String assignedTime;

    @ApiModelProperty(value = "结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private String removeTime;
}
