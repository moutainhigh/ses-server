package com.redescooter.ses.api.scooter.vo;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName:ScooterRecordListEnter
 * @description: ScooterRecordListEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/27 10:30
 */
@ApiModel(value = "查询车辆记录入参", description = "查询车辆记录入参")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
@Data
public class ScooterRecordListEnter extends PageEnter {
    @ApiModelProperty(value = "")
    @NotNull
    private Long scooterId;
}
