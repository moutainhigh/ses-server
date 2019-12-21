package com.redescooter.ses.api.foundation.vo.common;

import com.redescooter.ses.api.common.vo.base.PageEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * description: CityResult
 * author: jerry.li
 * create: 2019-05-27 16:41
 */

@ApiModel(value = "城市区域参数", description = "查询条件")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class CityByPageEnter extends PageEnter {

    @ApiModelProperty(value = "主键id")
    private Long id;
    /**
     * 父ID，默认首级节点为0
     */
    @ApiModelProperty(value = "父ID")
    private Long pId;

}
