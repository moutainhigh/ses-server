package com.redescooter.ses.web.ros.vo.wms.cn;

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
 * @ClassName:WhOutSaveEnter
 * @description: WhOutSaveEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/07/20 12:09
 */
@ApiModel(value = "新增出库单", description = "新增出库单")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class WhOutSaveEnter extends GeneralEnter {

    @ApiModelProperty(value = "目标仓库", required = true)
    @NotNull(code = ValidationExceptionCode.GOGAL_WH_IS_EMPTY, message = "目标仓库为空")
    private Long gogalId;

    @ApiModelProperty(value = "收货人", required = true)
    @NotNull(code = ValidationExceptionCode.CONSIGNEE_ID_IS_EMPTY, message = "收货人为空")
    private Long consigneeId;

    @ApiModelProperty(value = "通知人", required = true)
    private String notifyFirstName;

    @ApiModelProperty(value = "通知人", required = true)
    private String notifyLastName;

    @ApiModelProperty(value = "物流方式", required = true)
    @NotNull(code = ValidationExceptionCode.CONSIGN_TYPE_IS_EMPTY, message = "物流方式为空")
    private String consignType;

    @ApiModelProperty(value = "产品json", required = true)
    @NotNull(code = ValidationExceptionCode.PRODUCT_LIST_IS_EMPTY, message = "产品部件为空")
    private String productList;
}
