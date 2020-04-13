package com.redescooter.ses.web.ros.vo.production.assembly;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:AssemblyQcItemDetailItemResult
 * @description: AssemblyQcItemDetailItemResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/30 15:58
 */
@ApiModel(value = "QC条目详情质检项", description = "QC条目详情质检项")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class AssemblyQcItemDetailItemResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "质检项")
    private String item;

    @ApiModelProperty(value = "结果")
    private String result;
}
