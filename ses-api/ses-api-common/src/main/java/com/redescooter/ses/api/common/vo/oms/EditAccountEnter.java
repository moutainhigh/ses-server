package com.redescooter.ses.api.common.vo.oms;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "OMS修改用户信息", description = "OMS修改用户信息")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
public class EditAccountEnter extends SaveAccountEnter{

    @ApiModelProperty(value="id")
    private Long id;
}
