package com.redescooter.ses.web.ros.vo.bom.combination;

import com.redescooter.ses.api.common.vo.base.PageEnter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName:CombinationListEnter
 * @description: CombinationListEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/02/25 13:29
 */
@ApiModel(value = "组合列表入参", description = "组合列表入参")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class CombinationListEnter extends PageEnter {

    @ApiModelProperty(value = "关键字搜索",required = false)
    private String keyword;

    @ApiModelProperty("库存类型,1:中国，2：法国")
    private Integer stockType;
}
