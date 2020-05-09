package com.redescooter.ses.api.common.vo;


import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @description: CountByStatusResult
 * @author: Darren
 * @create: 2019/08/16 18:37
 */
@ApiModel("状态统计")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class CountByStatusResult extends GeneralResult {

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "总数")
    private int totalCount;
}
