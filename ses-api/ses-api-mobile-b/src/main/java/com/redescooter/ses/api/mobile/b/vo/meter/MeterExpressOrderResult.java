package com.redescooter.ses.api.mobile.b.vo.meter;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName:MeterExpressOrderResult
 * @description: MeterExpressOrderResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/11/16 17:42 
 */
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class MeterExpressOrderResult extends GeneralResult {
    @ApiModelProperty(value="id")
    private Long id;

    @ApiModelProperty(value="批次号")
    private String batchNo;


    @ApiModelProperty(value="订单号")
    private String orderNo;

    @ApiModelProperty(value="状态，见TAPD")
    private String status;


    @ApiModelProperty(value="收货方国家")
    private String recipientCountry;


    @ApiModelProperty(value="收件方省份")
    private String recipientProvince;


    @ApiModelProperty(value="收件方城市")
    private String recipientCity;


    @ApiModelProperty(value="收件方邮编")
    private String recipientPostcode;


    @ApiModelProperty(value="收件方详细地址")
    private String recipientAddress;


    @ApiModelProperty(value="收件方纬度")
    private BigDecimal recipientLatitude;


    @ApiModelProperty(value="收货方经度")
    private BigDecimal recipientLongitude;


    @ApiModelProperty(value="GeoHash")
    private String recipientGeohash;

    @ApiModelProperty(value="期望送达时间,注意这是时间段")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date expectTimeBegin;


    @ApiModelProperty(value="期望送达时间,注意这是时间段")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date expectTimeEnd;

    @ApiModelProperty(value="剩余订单数，包含当前正在进行的订单")
    private int remainingOrderNum;
}
