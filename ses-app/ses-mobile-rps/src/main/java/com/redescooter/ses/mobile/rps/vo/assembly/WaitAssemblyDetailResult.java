package com.redescooter.ses.mobile.rps.vo.assembly;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:WaitAssemblyDetailResult
 * @description: WaitAssemblyDetailResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/13 19:38
 */
@ApiModel(value = "", description = "")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class WaitAssemblyDetailResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "组装单")
    private Long assemblyId;

    @ApiModelProperty(value = "产品名称")
    private String productCnName;

    @ApiModelProperty(value = "产品编号")
    private String productN;

    @ApiModelProperty(value = "待组装数量")
    private int waitAssemblyQty;
}
