package com.redescooter.ses.web.ros.vo.production.assembly;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

import java.util.List;

/**
 * @ClassName:AssemblyQcItemViewResult
 * @description: AssemblyQcItemViewResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/24 16:18
 */
@ApiModel(value = "产品质检结果详细信息", description = "产品质检结果详细信息")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class AssemblyQcItemViewResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "质检结果")
    private String qcResult;

    @ApiModelProperty(value = "序列号")
    private String serialN;

    @ApiModelProperty(value = "质检记录")
    List<QcItemViewResult> qcItemViewResultList;
}
