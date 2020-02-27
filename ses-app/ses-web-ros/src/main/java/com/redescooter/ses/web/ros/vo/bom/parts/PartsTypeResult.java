package com.redescooter.ses.web.ros.vo.bom.parts;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @ClassName PartsTypeResult
 * @Author Jerry
 * @date 2020/02/27 15:50
 * @Description:
 */

@ApiModel(value = "部品类型结果", description = "部品类型结果")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class PartsTypeResult extends GeneralResult {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "部品类型名称")
    private String name;

    @ApiModelProperty(value = "部品类型编码")
    private String code;

    @ApiModelProperty(value = "部品类型编码")
    private int value;
}
