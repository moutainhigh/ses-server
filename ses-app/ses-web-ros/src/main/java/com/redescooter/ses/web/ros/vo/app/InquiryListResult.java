package com.redescooter.ses.web.ros.vo.app;

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

    @ApiModelProperty(value = "询价单id")
    private Long id;

    @ApiModelProperty(value = "询价单号")
    private String orderNo;

    @ApiModelProperty(value = "状态 1待处理 2处理中 3已完成")
    private Integer status;

    @ApiModelProperty(value = "车型名称")
    private String scooterName;

    @ApiModelProperty(value = "车型id")
    private Long specificatTypeId;

    @ApiModelProperty(value = "颜色id")
    private Long colorId;

    @ApiModelProperty(value = "颜色名称")
    private String colorName;

    @ApiModelProperty(value = "电池数量")
    private Integer batteryNum;

    @ApiModelProperty(value = "车座")
    private Integer seatNumber;

    @ApiModelProperty(value = "客户姓名")
    private String customerName;

    @ApiModelProperty(value = "客户id")
    private Long customerId;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdTime;

    @ApiModelProperty(value = "处理中走到哪一步 0车辆信息 1电池信息 2绑定VIN 3设置软体 4完成")
    private Integer appNode;

    private Integer flag;

    private String battery;

    @ApiModelProperty(value = "处理中 已录入的电池数量")
    private Integer hasInputBatteryNum;

    @ApiModelProperty(value = "处理中 未录入的电池数量")
    private Integer noInputBatteryNum;

}
