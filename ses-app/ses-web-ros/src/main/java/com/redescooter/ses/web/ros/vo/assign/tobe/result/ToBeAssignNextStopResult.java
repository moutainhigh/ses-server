package com.redescooter.ses.web.ros.vo.assign.tobe.result;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Description 待分配点击下一步出参
 * @Author Chris
 * @Date 2020/12/28 11:28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "待分配点击下一步出参", description = "待分配点击下一步出参")
public class ToBeAssignNextStopResult extends GeneralResult implements Serializable {

    private static final long serialVersionUID = 5504464406442564370L;

    @ApiModelProperty(value = "车辆信息")
    private List<ToBeAssignNextStopDetailResult> list;

}

