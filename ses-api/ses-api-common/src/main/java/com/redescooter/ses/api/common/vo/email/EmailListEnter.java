package com.redescooter.ses.api.common.vo.email;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description
 * @Author Chris
 * @Date 2021/2/4 14:26
 */

@ApiModel(value = "Email List Enter", description = "Email List Enter")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class EmailListEnter extends GeneralEnter {

    @ApiModelProperty(value = "值")
    private String keyword;

    @ApiModelProperty(value = "状态")
    private String status;

}
