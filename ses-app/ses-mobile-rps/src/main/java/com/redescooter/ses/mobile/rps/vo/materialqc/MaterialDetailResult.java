package com.redescooter.ses.mobile.rps.vo.materialqc;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:MaterialDetailResult
 * @description: MaterialDetailResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/13 16:53
 */
@ApiModel(value = "来料质检详情", description = "来料质检详情")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class MaterialDetailResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "部件id")
    private Long partId;

    @ApiModelProperty(value = "部件名称")
    private String partCnName;

    @ApiModelProperty(value = "部件编号")
    private String partN;

    @ApiModelProperty(value = "部品质检方式")
    private Boolean idClass;

    @ApiModelProperty(value = "待质检数量")
    private int laveWaitQcQty;
}
