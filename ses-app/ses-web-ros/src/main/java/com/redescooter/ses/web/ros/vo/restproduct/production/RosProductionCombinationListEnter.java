package com.redescooter.ses.web.ros.vo.restproduct.production;

import com.redescooter.ses.api.common.vo.base.PageEnter;
import lombok.*;

import io.swagger.annotations.*;

@ApiModel(value = "组合产品列表", description = "组合产品列表")
@Data // 生成getter,setter等函数
@AllArgsConstructor // 生成全参数构造函数
@NoArgsConstructor // 生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class RosProductionCombinationListEnter extends PageEnter {

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "关键字")
    private String keyword;

    @ApiModelProperty(value = "table类型")
    private Integer classType;
}
