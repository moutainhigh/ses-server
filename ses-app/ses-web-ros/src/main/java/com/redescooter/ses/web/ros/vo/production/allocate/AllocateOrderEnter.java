package com.redescooter.ses.web.ros.vo.production.allocate;

import com.redescooter.ses.api.common.vo.base.PageEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @ClassName:AllocateOrderEnter
 * @description: AllocateOrderEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/19 15:21
 */
@ApiModel(value = "调拨单列表入参", description = "调拨单列表入参")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class AllocateOrderEnter extends PageEnter {

    @ApiModelProperty(value = "类型", required = true)
    private String type;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "创建开始时间")
    private Date createdStartTime;

    @ApiModelProperty(value = "创建结束时间")
    private Date createdEndTime;

    @ApiModelProperty(value = "关键字")
    private String keyword;
}
