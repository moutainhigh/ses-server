package com.redescooter.ses.wh.fr.vo;

import com.redescooter.ses.api.common.vo.base.PageEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Description
 * @Author Chris
 * @Date 2021/5/10 10:33
 */
@Data
@ApiModel(value = "仓库账号列表入参")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class WarehouseAccountListEnter extends PageEnter {

    @ApiModelProperty(value = "账号类型 1公司账号 2第三方账号")
    private Integer type;

    @ApiModelProperty(value = "状态 0全部状态 1开启 2关闭")
    private Integer status;

    @ApiModelProperty(value = "系统 0全部系统 1FR 2CH")
    private Integer system;

    @ApiModelProperty(value = "关键字")
    private String keyword;

}
