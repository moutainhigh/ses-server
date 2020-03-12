package com.redescooter.ses.web.ros.vo.sys.position;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName:SavePositionEnter
 * @description: SavePositionEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/06 17:48
 */
@ApiModel(value = "保存职位", description = "保存职位")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class SavePositionEnter extends GeneralEnter {

    @ApiModelProperty(value = "职位Id")
    private Long deptId;

    @ApiModelProperty(value = "名字")
    private String name;

    @ApiModelProperty(value = "描述")
    private String desc;

    @ApiModelProperty(value = "销售区域")
    private List<Long> salesTerritoryList;

    @ApiModelProperty(value = "页面权限")
    private List<Long> pagePermissionList;
}
