package com.redescooter.ses.web.ros.vo.website;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
public class OrderFormResult extends GeneralResult {
    @ApiModelProperty(value = "询价单id")
    private Long id;

    @ApiModelProperty(value = "国家电话代码")
    private String countryCode;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "产品类型Id")
    private Long productId;

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

    @ApiModelProperty(value = "过期月份")
    private Integer expiredMonth;

    @ApiModelProperty(value = "过期年分")
    private Integer expiredYear;

    @ApiModelProperty(value = "cvv")
    private String cvv;

    @ApiModelProperty(value = "安全码")
    private String postalCode;
}
