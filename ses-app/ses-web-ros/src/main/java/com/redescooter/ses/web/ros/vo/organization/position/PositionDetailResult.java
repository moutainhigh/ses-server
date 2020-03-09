package com.redescooter.ses.web.ros.vo.organization.position;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName:PositionDetailResult
 * @description: PositionDetailResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/06 17:54
 */
@ApiModel(value = "职位权限页", description = "职位权限页")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class PositionDetailResult extends GeneralResult {

    @ApiModelProperty(value = "负责的销售区域")
    private List<Long> salesTerritoryList;

    @ApiModelProperty(value = "页面权限")
    private List<Long> pagePermissionList;
}
