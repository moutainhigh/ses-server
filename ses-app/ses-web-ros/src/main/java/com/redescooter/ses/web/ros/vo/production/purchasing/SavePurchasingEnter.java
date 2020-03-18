package com.redescooter.ses.web.ros.vo.production.purchasing;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.ros.exception.ValidationExceptionCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @ClassName:SavePurchasingEnter
 * @description: SavePurchasingEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/18 17:27
 */
@ApiModel(value = "保存采购单", description = "保存采购单")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
public class SavePurchasingEnter extends GeneralEnter {
    @ApiModelProperty(value = "收货人id", required = true)
    @NotNull(code = ValidationExceptionCode.CONSIGNEE_ID__IS_EMPTY, message = "收货人为空")
    private Long consigneeId;

    @ApiModelProperty(value = "工厂Id", required = true)
    @NotNull(code = ValidationExceptionCode.FACTORY_ID_EMPTY, message = "工厂Id为空")
    private Long factoryId;

    @ApiModelProperty(value = "部件列表 格式：id：10000，qty：100", required = true)
    @NotNull(code = ValidationExceptionCode.PARTS_IS_EMPTY, message = "部件为空")
    private String partList;

    @ApiModelProperty(value = "付款方式", required = true)
    @NotNull(code = ValidationExceptionCode.PAYMENT_TYPE_IS_EMPTY, message = "付款方式为空")
    private String paymentType;

    @ApiModelProperty(value = "付款时间")
    private Date statementdate;

    @ApiModelProperty(value = "累加多少天")
    private Integer statementDay;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "付款周期，格式：estimatedPaymentDate：2020-3-2 00：00：00，paymentRatio:20.2，price:20.1,remark:123")
    private String paymentInfoList;
}
