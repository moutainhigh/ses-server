package com.redescooter.ses.web.delivery.vo;

import com.redescooter.ses.api.common.annotation.MaximumLength;
import com.redescooter.ses.api.common.annotation.MinimumLength;
import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.annotation.Regexp;
import com.redescooter.ses.api.common.constant.RegexpConstant;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.delivery.exception.ValidationExceptionCode;
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
    @NotNull(code = ValidationExceptionCode.ID_IS_EMPTY, message = "Id 为空")
    private Long driverId;

    @ApiModelProperty(value = "收件人")
    @NotNull(code = ValidationExceptionCode.RECIPIENT_IS_EMPTY, message = "收件人为空")
    @MinimumLength(code = ValidationExceptionCode.RECIPIENT_IS_UNAVAILABLE, message = "姓字符长度为2-20字符")
    @MaximumLength(code = ValidationExceptionCode.RECIPIENT_IS_UNAVAILABLE, message = "姓字符长度为2-20字符")
    @Regexp(value = RegexpConstant.name,code = ValidationExceptionCode.RECIPIENT_IS_UNAVAILABLE, message = "姓字非法字符")
    private String recipient;

//    @ApiModelProperty(value = "收件人邮箱")
//    @NotNull(code = ValidationExceptionCode.RECIPIENT_IS_EMPTY, message = "收件人邮箱")
//    @MinimumLength(code = ValidationExceptionCode.EMAIL_IS_UNAVAILABLE, message = "邮箱字符长度为2-20字符")
//    @MaximumLength(code = ValidationExceptionCode.EMAIL_IS_UNAVAILABLE, message = "邮箱字符长度为2-20字符")
//    private String recipientEmail;

    @ApiModelProperty(value = "手机国家区号")
    @NotNull(code = ValidationExceptionCode.COUNTRY_CODE_IS_EMPTY, message = "手机国家区号为空")
    private String countryCode;

    @ApiModelProperty(value = "收件人电话")
    @NotNull(code = ValidationExceptionCode.PHONE_IS_EMPTY, message = "电话为空")
    @MinimumLength(code = ValidationExceptionCode.PHONE_IS_UNAVAILABLE, message = "电话字符长度为2-20字符")
    @MaximumLength(code = ValidationExceptionCode.PHONE_IS_UNAVAILABLE, message = "电话字符长度为2-20字符")
    @Regexp(value = RegexpConstant.number,code = ValidationExceptionCode.PHONE_IS_UNAVAILABLE, message = "电话字符长度为2-20字符")
    private String recipientTel;

    @ApiModelProperty(value = "收件人地址")
    @NotNull(code = ValidationExceptionCode.ADDRESS_IS_EMPTY, message = "收件人地址为空")
    @MinimumLength(code = ValidationExceptionCode.ADDRESS_IS_UNAVAILABLE, message = "收件人地址字符长度为2-20字符")
    @MaximumLength(code = ValidationExceptionCode.ADDRESS_IS_UNAVAILABLE, message = "收件人地址字符长度为2-20字符")
    private String recipientAddress;

    @ApiModelProperty(value = "经度")
    @NotNull(code = ValidationExceptionCode.LNG_IS_EMPTY, message = "经度为空")
    private String longitude;

    @ApiModelProperty(value = "纬度")
    @NotNull(code = ValidationExceptionCode.LAT_IS_EMPTY, message = "纬度为空")
    private String latitude;

    @ApiModelProperty(value = "门牌信息")
    private String houseInfo;

    @ApiModelProperty(value = "包裹数量")
    @Regexp(value=RegexpConstant.twoNumber,code = ValidationExceptionCode.DATE_IS_ILLEGAL,message = "数据非法")
    private Integer parcelQuantity = 1;

    @ApiModelProperty(value = "商品清单")
    private String goodsInventory;

    @ApiModelProperty(value = "预约时长")
    private String Appointment;

    @ApiModelProperty(value = "超时时长")
    private String timeoutExpectde = "0";
}
