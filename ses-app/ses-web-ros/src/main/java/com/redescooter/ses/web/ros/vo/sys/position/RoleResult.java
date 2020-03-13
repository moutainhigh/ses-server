package com.redescooter.ses.web.ros.vo.sys.position;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
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
 * @create: 2020/03/06 17:41
 */
@ApiModel(value = "职位出参", description = "职位出参")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class RoleResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "名字")
    private String name;

    @ApiModelProperty(value = "描述")
    private String desc;
}
