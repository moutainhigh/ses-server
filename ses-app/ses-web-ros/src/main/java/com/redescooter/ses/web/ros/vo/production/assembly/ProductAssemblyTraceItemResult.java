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
 * @ClassName:ProductAssemblyTraceItemResult
 * @description: ProductAssemblyTraceItemResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/24 14:34
 */
@ApiModel(value = "组装记录条目", description = "组装记录条目")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class ProductAssemblyTraceItemResult extends GeneralResult {

    @ApiModelProperty(value = "组装数量")
    private int assemblyCompleteTotal;

    @ApiModelProperty(value = "组装时间")
    private Date assemblyDate;

}
