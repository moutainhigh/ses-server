package com.redescooter.ses.web.ros.vo.restproduct;

import com.redescooter.ses.api.common.vo.base.PageEnter;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Data // 生成getter,setter等函数
@AllArgsConstructor // 生成全参数构造函数
@NoArgsConstructor // 生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class RosProductionProductPartListEnter extends PageEnter {
    @ApiModelProperty(value = "模糊搜索", required = false)
    private String keyword;

    @ApiModelProperty(value = "区域代码", required = false)
    private Long secId;
}
