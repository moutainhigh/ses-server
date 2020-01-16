package com.redescooter.ses.web.delivery.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 3/1/2020 5:00 下午
 * @ClassName: ListDeliveryPage
 * @Function: TODO
 */
@ApiModel(value = "配送单列表结果", description = "配送单列表结果")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class ListDeliveryResult extends GeneralResult {

    @ApiModelProperty(value = "ID")
    private Long id;
    @ApiModelProperty(value = "司机主键")
    private Long driverId;
    @ApiModelProperty(value = "车辆id")
    private Long scooterId;
    @ApiModelProperty(value = "订单编号")
    private String orderNo;
    @ApiModelProperty(value = "订单状态")
    private String status;
    @ApiModelProperty(value = "状态标签,0没有，1超时")
    private String label;
    @ApiModelProperty(value = "收件人")
    private String recipient;
    @ApiModelProperty(value = "收件人邮箱")
    private String recipientEmail;
    @ApiModelProperty(value = "手机国家区号")
    private String countryCode;
    @ApiModelProperty(value = "收件人电话")
    private String recipientTel;
    @ApiModelProperty(value = "收件人地址")
    private String recipientAddress;
    @ApiModelProperty(value = "经度")
    private String longitude;
    @ApiModelProperty(value = "纬度")
    private String latitude;
    @ApiModelProperty(value = "门牌信息")
    private String houseInfo;
    @ApiModelProperty(value = "商品清单")
    private String goodsInventory;
    @ApiModelProperty(value = "超时时长")
    private String timeoutExpectde;
    @ApiModelProperty(value = "司机姓氏")
    private String driverFirstName;
    @ApiModelProperty(value = "司机名字")
    private String driverLastName;
    @ApiModelProperty(value = "车牌号")
    private String licensePlate;
    @ApiModelProperty(value = "包裹数量")
    private Integer parcelQuantity = 1;
    @ApiModelProperty(value = "订单创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date createdTime;
    @ApiModelProperty(value = "订单完成时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date deliveredTime;
}
