package com.redescooter.ses.web.ros.vo.restproduct.production;

import com.redescooter.ses.api.common.annotation.NotEmpty;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.web.ros.exception.ValidationExceptionCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@ApiModel(value = "车辆列表入参", description = "车辆列表入参")
@Data // 生成getter,setter等函数
@AllArgsConstructor // 生成全参数构造函数
@NoArgsConstructor // 生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class RosProductionScooterListEnter extends PageEnter {

    @ApiModelProperty(value = "页面类型 草稿bom", allowableValues = "1,2")
    @NotEmpty(code = ValidationExceptionCode.TYPE_IS_EMPTY, message = "table 为空")
    private Integer classType;

    @ApiModelProperty(value = "分组Id")
    private Long groupId;

    @ApiModelProperty(value = "颜色Id")
    private Long colorId;

    @ApiModelProperty(value = "产品列表")
    private Integer status;

    @ApiModelProperty(value = "关键字")
    private String keyword;
}
