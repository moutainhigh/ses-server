package com.redescooter.ses.web.ros.vo.production.assembly;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName:AssemblyQcItemTraceResult
 * @description: AssemblyQcItemTraceResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/30 17:14
 */
@ApiModel(value = "产品条目质检记录", description = "产品条目质检记录")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class AssemblyQcItemTraceResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "Qc状态")
    private String status;

    @ApiModelProperty(value = "质检通过记录")
    private List<AssemblyQcItemDetailResult> passAssemblyQcItemDetailResultList;

    @ApiModelProperty(value = "质检失败记录")
    private List<AssemblyQcItemDetailResult> failAssemblyQcItemDetailResultList;
}
