package com.redescooter.ses.web.ros.vo.website;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.constant.DateConstant;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName:OrderFormResult
 * @description: OrderFormResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/05/13 19:23
 */
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class OrderFormInfoResult extends GeneralResult {
    @ApiModelProperty(value = "询价单id")
    private Long id;

    @ApiModelProperty(value = "订单号")
    private String orderNo;

    @ApiModelProperty(value = "国家电话代码")
    private String countryCode;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "产品Id")
    private Long productId;

    @ApiModelProperty(value = "产品类型")
    private String produceModel;

    @ApiModelProperty(value = "产品数量")
    private Integer productQty;

    @ApiModelProperty(value = "配件电池Id")
    private Long accessoryBatteryId;

    @ApiModelProperty(value = "配件数量")
    private Integer accessoryBatteryQty;

    @ApiModelProperty(value = "后备箱Id")
    private Long topCaseId;

    @ApiModelProperty(value = "银行卡名称")
    private String bankCardName;

    @ApiModelProperty(value = "卡号")
    private String cardNum;

    @ApiModelProperty(value = "过期年分")
    @DateTimeFormat(pattern = DateConstant.DEFAULT_DATETIME_FORMAT)
    @JsonFormat(pattern = DateConstant.DEFAULT_DATETIME_FORMAT, timezone = DateConstant.UTC)
    private Date expiredTime;

    @ApiModelProperty(value = "cvv")
    private String cvv;

    @ApiModelProperty(value = "邮编")
    private String postalCode;

    @ApiModelProperty(value = "总价格")
    private BigDecimal totalPrice;

    @ApiModelProperty(value = "剩余价格")
    private BigDecimal remainingPrice;

    @ApiModelProperty(value = "颜色")
    private String color;

    @ApiModelProperty(value = "状态")
    private String status;
}
