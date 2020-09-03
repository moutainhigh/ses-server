package com.redescooter.ses.web.ros.vo.wms.cn;

import com.redescooter.ses.api.common.annotation.MaximumLength;
import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.annotation.Regexp;
import com.redescooter.ses.api.common.constant.RegexpConstant;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.ros.exception.ValidationExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:StartWhOutOrderEnter
 * @description: StartWhOutOrderEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/07/20 11:56
 */
@ApiModel(value = "开始出库单入参", description = "开始出库单入参")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class StartWhOutOrderEnter extends GeneralEnter {

    @ApiModelProperty(value = "id", required = true)
    @NotNull(code = com.redescooter.ses.api.common.exception.ValidationExceptionCode.ID_IS_EMPTY, message = "id 不存在")
    private Long id;

    @ApiModelProperty(value = "空运方式")
    private String airParcelType;

    @ApiModelProperty(value = "收获公司", required = true)
    @NotNull(code = ValidationExceptionCode.CONSIGN_COMPANY_NAME_IS_EMPTY, message = "物流公司为空")
    @MaximumLength(value = "50",code =ValidationExceptionCode.CONSIGN_COMPANY_NAME_ILLEGAL,message ="物流公司名称非法")
    @Regexp(code =ValidationExceptionCode.CONSIGN_COMPANY_NAME_ILLEGAL,message ="物流公司名称非法")
    private String consignCompany;

    @ApiModelProperty(value = "物流单号", required = true)
    @NotNull(code = ValidationExceptionCode.TRACKING_NUM_IS_EMPTY, message = "物流单号为空")
    @Regexp(value = RegexpConstant.ORDER_NUMBER,code =ValidationExceptionCode.TRACKING_NUM_ILLEGAL,message ="物流单号非法")
    private String trackingN;

    @ApiModelProperty(value = "物流附件", required = true)
    @NotNull(code = ValidationExceptionCode.ANNEX_IS_EMPTY, message = "附件为空")
    private String annex;

    @ApiModelProperty(value = "物流金额", required = true)
    @NotNull(code = ValidationExceptionCode.PRICE_IS_EMPTY, message = "价格为空")
    @Regexp(value = RegexpConstant.MONEY,code =ValidationExceptionCode.PRICE_ILLEGAL,message ="价格输入非法")
    private String price;
}
