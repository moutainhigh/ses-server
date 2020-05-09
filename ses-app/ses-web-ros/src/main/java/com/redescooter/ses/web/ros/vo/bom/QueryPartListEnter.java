package com.redescooter.ses.web.ros.vo.bom;

import com.redescooter.ses.api.common.vo.base.PageEnter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName:SaveScooterPartListEnter
 * @description: SaveScooterPartListEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/02/25 12:35
 */
@ApiModel(value = "保存整车部件列表入参", description = "保存整车部件列表入参")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class QueryPartListEnter extends PageEnter {
    @ApiModelProperty(value = "模糊搜索",required = false)
    private String keyword;

    @ApiModelProperty(value = "区域代码",required = false)
    private String sec;
}
