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
 * @ClassName:ScooterListEnter
 * @description: ScooterListEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/02/25 10:22
 */
@ApiModel(value = "Bom 车辆列表入参", description = "Bom 车辆列表入参")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ScooterListEnter extends PageEnter {
    @ApiModelProperty(value = "关键字搜索",required = false)
    private String keyword;
}
