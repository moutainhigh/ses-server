package com.redescooter.ses.web.ros.vo.website;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.ros.exception.ValidationExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:SaveInquiryEnter
 * @description: SaveInquiryEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/05/12 16:07
 */
@ApiModel(value = "", description = "")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SaveSaleOrderEnter extends GeneralEnter {

    @ApiModelProperty(value = "产品类型Id")
    @NotNull(code = ValidationExceptionCode.TYPE_IS_EMPTY, message = "产品类型为空")
    private Long productId;

    @ApiModelProperty(value = "产品数量")
    @NotNull(code = ValidationExceptionCode.QTY_IS_EMPTY, message = "产品数量为空")
    private Integer productQty;

    @ApiModelProperty(value = "配件电池Id")
    @NotNull(code = ValidationExceptionCode.BATTERY_ID, message = "电池Id 为空")
    private Long accessoryBatteryId;

    @ApiModelProperty(value = "配件数量")
    private Integer accessoryBatteryQty;

    @ApiModelProperty(value = "后备箱Id")
    @NotNull(code = ValidationExceptionCode.TOPCASE_ID, message = "后备箱Id 为空")
    private Long topCaseId;
}
