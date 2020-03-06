package com.redescooter.ses.web.ros.vo.position;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:PositionListEnter
 * @description: PositionListEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/06 17:34
 */
@ApiModel(value = "职位列表", description = "职位列表")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class PositionListEnter extends GeneralEnter {
    @ApiModelProperty(value = "部门Id")
    private Long deptId;

    @ApiModelProperty(value = "关键字")
    private String keyword;
}
