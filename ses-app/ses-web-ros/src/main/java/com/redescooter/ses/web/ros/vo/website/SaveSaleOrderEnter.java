package com.redescooter.ses.web.ros.vo.website;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.annotation.MaximumLength;
import com.redescooter.ses.api.common.annotation.MinimumLength;
import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.constant.DateConstant;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.ros.exception.ValidationExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @ClassName:SaveInquiryEnter
 * @description: SaveInquiryEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/05/12 16:07
 */
@ApiModel(value = "保存预订单", description = "保存预订单")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SaveSaleOrderEnter extends GeneralEnter {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "国家电话代码")
    private String countryCode;

    @ApiModelProperty(value = "电话")
    @NotNull(code = ValidationExceptionCode.CONTACT_PHONE_IS_EMPTY,message = "电话为空")
    @MinimumLength(value = "2",code = ValidationExceptionCode.CHARACTER_IS_TOO_SHORT,message = "字符过短")
    private String phone;

    @ApiModelProperty(value = "地址")
    @NotNull(code = ValidationExceptionCode.ADDRESS_CHAR_IS_NOT_ILLEGAL,message = "地址为空")
    @MinimumLength(value = "2",code = ValidationExceptionCode.CHARACTER_IS_TOO_SHORT,message = "字符过短")
    @MaximumLength(value = "120",code = ValidationExceptionCode.CHARACTER_IS_TOO_LONG,message = "字符过长")
    private String address;

    @ApiModelProperty(value = "产品Id")
    @NotNull(code = ValidationExceptionCode.TYPE_IS_EMPTY, message = "产品类型为空")
    private Long productId;

    @ApiModelProperty(value = "产品Id")
    @NotNull(code = ValidationExceptionCode.MODEL_IS_EMPTY, message = "产品类型为空")
    private String productModel;

    @ApiModelProperty(value = "产品数量")
    @NotNull(code = ValidationExceptionCode.QTY_IS_EMPTY, message = "产品数量为空")
    private Integer productQty;

    @ApiModelProperty(value = "配件电池Id")
    @NotNull(code = ValidationExceptionCode.BATTERY_ID_IS_EMPTY, message = "电池Id 为空")
    private Long accessoryBatteryId;

    @ApiModelProperty(value = "配件数量")
    private Integer accessoryBatteryQty;

    @ApiModelProperty(value = "是否购买后备箱")
    private Boolean buyTopCase;

    @ApiModelProperty(value = "后备箱Id")
    private Long topCaseId;

    @ApiModelProperty(value = "银行卡名称")
    @NotNull(code = ValidationExceptionCode.BANKCARD_NAME_IS_EMPTY, message = "银行卡上姓名为空")
    private String bankCardName;

    @ApiModelProperty(value = "卡号")
    //@NotNull(code = ValidationExceptionCode.CARD_NUMBER_IS_EMPTY, message = "卡号为空")
    private String cardNum;

    @ApiModelProperty(value = "过期时间")
    //@NotNull(code = ValidationExceptionCode.EXPIRED_TIME_IS_EMPTY, message = "过期时间为空")
    private Long expiredTime;

    @ApiModelProperty(value = "cvv")
    //@NotNull(code = ValidationExceptionCode.CVV_IS_EMPTY, message = "CVV为空")
    private String cvv;

    @ApiModelProperty(value = "邮编")
    //@NotNull(code = ValidationExceptionCode.POSTAL_CODE_IS_EMPTY, message = "邮编为空")
    private String postalCode;
}
