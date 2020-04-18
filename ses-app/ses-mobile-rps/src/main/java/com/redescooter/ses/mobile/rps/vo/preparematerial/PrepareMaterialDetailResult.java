package com.redescooter.ses.mobile.rps.vo.preparematerial;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:PrepareMaterialDetailResult
 * @description: PrepareMaterialDetailResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/13 19:08
 */
@ApiModel(value = "待备料详情出参", description = "待备料详情出参")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class PrepareMaterialDetailResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "部件Id")
    private Long partId;

    @ApiModelProperty(value = "部件编号")
    private String partN;

    @ApiModelProperty(value = "部件名称")
    private String partCnName;

    @ApiModelProperty(value = "待备料数量")
    private int preparationWaitQty;
}
