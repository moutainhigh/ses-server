package com.redescooter.ses.api.common.vo.base;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Data // 生成getter,setter等函数
@AllArgsConstructor // 生成全参数构造函数
@NoArgsConstructor // 生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "分页公共参数", description = "分页公共参数")
public class OperatingEnter<T> extends PageEnter {
    @ApiModelProperty(value = "序列化ID", hidden = true)
    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "关键字")
    private String keyword;


    /**
     * 查询条件
     */
    @ApiModelProperty(value = "查询状态")
    private Integer status;


}
