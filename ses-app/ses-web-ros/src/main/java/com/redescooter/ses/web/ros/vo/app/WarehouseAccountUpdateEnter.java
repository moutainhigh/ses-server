package com.redescooter.ses.web.ros.vo.app;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Description
 * @Author Chris
 * @Date 2021/5/10 10:42
 */
@Data
@ApiModel(value = "仓库账号修改入参")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class WarehouseAccountUpdateEnter extends GeneralEnter {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "使用者")
    private String name;

    @ApiModelProperty(value = "职位")
    private String position;

    @ApiModelProperty(value = "可操作系统 1FR 2CH")
    private Integer system;

}
