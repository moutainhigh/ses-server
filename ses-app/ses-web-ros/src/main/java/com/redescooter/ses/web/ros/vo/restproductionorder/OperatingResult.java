package com.redescooter.ses.web.ros.vo.restproductionorder;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 *  @author: alex
 *  @Date: 2020/10/26 14:18
 *  @version：V ROS 1.8.3
 *  @Description:
 */
@ApiModel(value = "操作动态", description = "操作动态")
@Data
@EqualsAndHashCode(callSuper = true)
public class OperatingResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "操作类型")
    private Integer operationType;

    @ApiModelProperty(value = "操作人")
    private Long operationUserId;

    @ApiModelProperty(value = "操作人名称")
    private String operationUserFirstName;

    @ApiModelProperty(value = "操作人名称")
    private String operationUserLastName;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "操作时间")
    private Date operateTime;
}
