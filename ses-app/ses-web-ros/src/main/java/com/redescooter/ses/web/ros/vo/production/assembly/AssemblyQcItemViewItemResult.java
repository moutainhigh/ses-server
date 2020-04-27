package com.redescooter.ses.web.ros.vo.production.assembly;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName:AssemblyQcItemViewItemResult
 * @description: AssemblyQcItemViewItemResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/27 17:17
 */
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "质检条目的质检项", description = "质检条目的质检项")
public class AssemblyQcItemViewItemResult extends GeneralResult {
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "质检项Id")
    private Long itemId;

    @ApiModelProperty(value = "质检项名称")
    private String itemName;

    @ApiModelProperty(value = "质检项结果Id")
    private Long qcResultId;

    @ApiModelProperty(value = "质检项名称")
    private String qcResultName;
}
