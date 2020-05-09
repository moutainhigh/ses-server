package com.redescooter.ses.mobile.rps.vo.materialqc;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:CheckPartQcBySerilaNResult
 * @description: CheckPartQcBySerilaNResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/05/09 18:56
 */
@ApiModel(value = "检查是否已经质检过出参", description = "检查是否已经质检过出参")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class CheckPartQcBySerilaNResult extends GeneralResult {

    @ApiModelProperty(value = "是否成功质检过")
    private Boolean whetherQc;
}
