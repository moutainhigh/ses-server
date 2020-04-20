package com.redescooter.ses.mobile.rps.vo.materialqc;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:SaveMaterialQcResult
 * @description: SaveMaterialQcResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/13 17:28
 */
@ApiModel(value = "保存质检结果出参", description = "保存质检结果出参")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SaveMaterialQcResult extends GeneralResult {

    @ApiModelProperty(value = "部件名称")
    private String partCnName;

    @ApiModelProperty(value = "部件编号")
    private String partN;

    @ApiModelProperty(value = "批次号")
    private String batchN;

    @ApiModelProperty(value = "剩余质检数量")
    private int laveWaitQcQty;

    @ApiModelProperty(value = "质检结果标识")
    private String qcResult;
}
