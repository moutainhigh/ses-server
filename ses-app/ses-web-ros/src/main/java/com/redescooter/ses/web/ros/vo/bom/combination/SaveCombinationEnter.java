package com.redescooter.ses.web.ros.vo.bom.combination;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.ros.exception.ValidationExceptionCode;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName:SaveCombinationEnter
 * @description: SaveCombinationEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/02/25 14:09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class SaveCombinationEnter extends GeneralEnter {

    @ApiModelProperty(value = "id",required = true)
    private Long id;

    @ApiModelProperty(value = "产品编号",required = true)
    @NotNull(code = ValidationExceptionCode.PRODUCT_NUM_IS_EMPTY,message = "产品编号 为空")
    private String productN;

    @ApiModelProperty(value = "产品英文名",required = true)
    @NotNull(code = ValidationExceptionCode.PRODUCT_EN_NAME_IS_EMPTY,message = "产品英文名 为空")
    private String productEnName;

    @ApiModelProperty(value = "产品中文名",required = true)
    @NotNull(code = ValidationExceptionCode.PRODUCT_CN_NAME_IS_EMPTY,message = "产品中文名 为空")
    private String productCnName;

    @ApiModelProperty(value = "产品法文名",required = true)
    @NotNull(code = ValidationExceptionCode.PRODUCT_FR_NAME_IS_EMPTY,message = "产品法文名 为空")
    private String productFrName;

    @ApiModelProperty(value = "产品部件",required = true)
    private String partList;
}
