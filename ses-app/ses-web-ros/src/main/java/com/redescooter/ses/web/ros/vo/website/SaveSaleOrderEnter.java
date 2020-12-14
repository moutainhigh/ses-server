package com.redescooter.ses.web.ros.vo.website;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.annotation.MaximumLength;
import com.redescooter.ses.api.common.annotation.MinimumLength;
import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.annotation.Regexp;
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
 * @description: SaveAboutUsEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/05/12 16:07
 */
@ApiModel(value = "Advance order", description = "Advance order")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SaveSaleOrderEnter extends GeneralEnter {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "product Id")
    @NotNull(code = ValidationExceptionCode.TYPE_IS_EMPTY, message = "产品类型为空")
    private Long productId;

    @ApiModelProperty(value = "product model")
    @NotNull(code = ValidationExceptionCode.MODEL_IS_EMPTY, message = "产品类型为空")
    private String productModel;

    @ApiModelProperty(value = "product number")
    @NotNull(code = ValidationExceptionCode.QTY_IS_EMPTY, message = "产品数量为空")
    private Integer productQty;

    @ApiModelProperty(value = "Accessory battery Id")
    @NotNull(code = ValidationExceptionCode.BATTERY_ID_IS_EMPTY, message = "电池Id 为空")
    private Long accessoryBatteryId;

    @ApiModelProperty(value = "Accessory number")
    private Integer accessoryBatteryQty;

    @ApiModelProperty(value = "Do you want to buy a trunk")
    private Boolean buyTopCase;

    @ApiModelProperty(value = "Boot ID")
    private Long topCaseId;

    @ApiModelProperty(value = "Name of bank card")
    @NotNull(code = ValidationExceptionCode.BANKCARD_NAME_IS_EMPTY, message = "银行卡上姓名为空")
    @MinimumLength(value = "2",code = ValidationExceptionCode.BANKCARDNAME_IS_NOT_ILLEGAL,message = "银行卡上姓名不合法")
    @MaximumLength(value = "40",code = ValidationExceptionCode.BANKCARDNAME_IS_NOT_ILLEGAL,message = "银行卡上姓名不合法")
    private String bankCardName;

    @ApiModelProperty(value = "卡号",hidden = true)
    private String cardNum;

    @ApiModelProperty(value = "Expiration time")
    private Long expiredTime;

    @ApiModelProperty(value = "cvv")
    private String cvv;

}
