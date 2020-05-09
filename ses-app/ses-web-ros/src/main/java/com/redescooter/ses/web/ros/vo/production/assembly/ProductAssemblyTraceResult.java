package com.redescooter.ses.web.ros.vo.production.assembly;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

import io.swagger.annotations.*;

/**
 * @ClassName:ProductAssemblyTraceResult
 * @description: ProductAssemblyTraceResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/24 14:31
 */
@ApiModel(value = "组装记录", description = "组装记录")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class ProductAssemblyTraceResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "产品Id")
    private Long productId;

    @ApiModelProperty(value = "产品编码")
    private String productN;

    @ApiModelProperty(value = "名称")
    private String enName;

    @ApiModelProperty(value = "组装总数")
    private int assemblyTotal;

    @ApiModelProperty(value = "组装完成数")
    private int assemblyCompleteTotal;

    @ApiModelProperty(value = "是否已开始组装 下拉标记")
    private Boolean assemblyFlag;
}
