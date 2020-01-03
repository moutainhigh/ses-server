package com.redescooter.ses.web.delivery.vo;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 3/1/2020 3:26 下午
 * @ClassName: SaveOrderDeliveryEnter
 * @Function: TODO
 */
@ApiModel(value = "创建配送单", description = "创建配送单")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class SaveOrderDeliveryEnter extends GeneralEnter {

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "司机主键")
    private Long driverId;

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

    @ApiModelProperty(value = "包裹数量")
    private Integer parcelQuantity = 1;

    @ApiModelProperty(value = "商品清单")
    private String goodsInventory;

    @ApiModelProperty(value = "预约时长")
    private String Appointment;

    @ApiModelProperty(value = "超时时长")
    private String timeoutExpectde;
}
