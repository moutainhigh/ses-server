package com.redescooter.ses.web.ros.vo.wthdrawalsite;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@ApiModel(value = "Business switch Enter", description = "Business switch Enter")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
public class isSwitchEnter extends GeneralEnter {

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "Business status [0 open, 1 close]")
    private Integer businessStatus;

}
