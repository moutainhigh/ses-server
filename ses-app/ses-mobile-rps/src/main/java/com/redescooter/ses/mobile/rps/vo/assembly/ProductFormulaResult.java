package com.redescooter.ses.mobile.rps.vo.assembly;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:ScooterFormulaResult
 * @description: ScooterFormulaResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/13 19:49
 */
@ApiModel(value = "组装单产品配方出参", description = "组装单产品配方出参")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class ProductFormulaResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "部件Id")
    private Long partId;

    @ApiModelProperty(value = "产品质检方式")
    private Boolean idClass;

    @ApiModelProperty(value = "部件名称")
    private String partCnName;

    @ApiModelProperty(value = "部件编号")
    private String partN;

    @ApiModelProperty(value = "数量")
    private int qty;
}
