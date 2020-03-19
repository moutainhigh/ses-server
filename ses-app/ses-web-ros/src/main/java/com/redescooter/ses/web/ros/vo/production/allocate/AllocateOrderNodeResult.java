package com.redescooter.ses.web.ros.vo.production.allocate;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @ClassName:AllocateOrderNodeResult
 * @description: AllocateOrderNodeResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/19 15:27
 */
@ApiModel(value = "", description = "")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class AllocateOrderNodeResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "事件")
    private String event;

    @ApiModelProperty(value = "创建人")
    private Long createdBy;

    @ApiModelProperty(value = "创建人")
    private String createdByFirstName;

    @ApiModelProperty(value = "创建人")
    private String createdByLastName;

    @ApiModelProperty(value = "创建时间")
    private Date createdTime;

}
