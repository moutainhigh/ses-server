package com.redescooter.ses.web.delivery.vo;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName:TopTenEnter
 * @description: TopTenEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/04 11:49
 */
@ApiModel(value = "司机排行榜入参", description = "司机排行榜出参")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@Data
public class TopTenEnter extends GeneralEnter {
    @ApiModelProperty(value = "排行榜时间， 默认 false 当天查询，true 标识周查询")
    private Boolean date = Boolean.FALSE;
}
