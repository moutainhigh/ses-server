package com.redescooter.ses.api.common.vo.node;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.vo.base.PageResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Description
 * @Author Chris
 * @Date 2021/5/11 14:12
 */
@Data
@ApiModel(value = "询价单列表出参")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class InquiryListResult extends PageResult {

    @ApiModelProperty(value = "车辆id")
    private Long scooterId;

    @ApiModelProperty(value = "rsn")
    private String rsn;

    @ApiModelProperty(value = "状态 1处理中 2已完成")
    private Integer status;

    private String model;

    @ApiModelProperty(value = "车型名称")
    private String scooterName;

    private Long colorId;

    @ApiModelProperty(value = "颜色名称")
    private String colorName;

    private String battery;

    @ApiModelProperty(value = "电池数量")
    private Integer batteryNum;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdTime;

    @ApiModelProperty(value = "处理中走到哪一步 1录入车辆 2录入电池 3设置软体 4录入vin 5完成")
    private Integer appNode;

}
