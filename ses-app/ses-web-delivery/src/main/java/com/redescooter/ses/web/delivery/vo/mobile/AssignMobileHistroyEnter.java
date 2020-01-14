package com.redescooter.ses.web.delivery.vo.mobile;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:AssignMobileHistroyEnter
 * @description: AssignMobileHistroyEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/13 19:44
 */
@ApiModel(value = "分配车辆记录", description = "分配车辆记录")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class AssignMobileHistroyEnter extends GeneralEnter {

    @ApiModelProperty(value = "分配时间")
    private String assignStartTime;

    @ApiModelProperty(value = "分配时间")
    private String assignEndTime;

    @ApiModelProperty(value = "时间")
    private String removeStartTime;

    @ApiModelProperty(value = "")
    private String removeEndTime;

    @ApiModelProperty(value = "")
    private String keyword;

}
