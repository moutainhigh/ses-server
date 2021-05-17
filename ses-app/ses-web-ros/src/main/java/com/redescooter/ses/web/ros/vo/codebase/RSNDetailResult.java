package com.redescooter.ses.web.ros.vo.codebase;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @Description
 * @Author Chris
 * @Date 2021/5/14 11:29
 */
@Data
@ApiModel(value = "RSN详情出参")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class RSNDetailResult extends GeneralResult {

    @ApiModelProperty(value = "节点记录")
    private Map<String, String> nodeRecord;

    @ApiModelProperty(value = "车辆信息")
    private RSNDetailScooterResult scooterInfo;

}
