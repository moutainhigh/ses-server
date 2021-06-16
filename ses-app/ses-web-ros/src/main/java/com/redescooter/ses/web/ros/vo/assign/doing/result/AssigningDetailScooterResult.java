package com.redescooter.ses.web.ros.vo.assign.doing.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Description
 * @Author Chris
 * @Date 2021/5/11 12:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class AssigningDetailScooterResult {

    @ApiModelProperty(value = "型号")
    private String specificatName;

    @ApiModelProperty(value = "型号id")
    private Long specificatId;

    @ApiModelProperty(value = "count")
    private Integer totalCount;

    @ApiModelProperty(value = "车辆信息")
    private List<AssigningDetailScooterInfoResult> scooterList;

}
