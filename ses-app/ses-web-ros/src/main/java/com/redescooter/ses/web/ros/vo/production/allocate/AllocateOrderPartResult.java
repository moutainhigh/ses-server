package com.redescooter.ses.web.ros.vo.production.allocate;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:AllocateOrderPartResult
 * @description: AllocateOrderPartResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/19 15:31
 */
@ApiModel(value = "调拨单部件列表", description = "调拨单部件列表")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class AllocateOrderPartResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "部品号")
    private String partsN;

    @ApiModelProperty(value = "类型")
    private String type;

    @ApiModelProperty(value = "名称")
    private String enName;

    @ApiModelProperty(value = "名称")
    private String cnName;

    @ApiModelProperty(value = "数量")
    private Integer qty;

}
