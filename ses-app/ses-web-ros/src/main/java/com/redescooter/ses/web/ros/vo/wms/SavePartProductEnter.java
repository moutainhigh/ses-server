package com.redescooter.ses.web.ros.vo.wms;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:SavePartProductEnter
 * @description: SavePartProductEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/07/21 14:55
 */
@ApiModel(value = "出库单部件列表入参", description = "出库单部件列表入参")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SavePartProductEnter extends GeneralEnter {

    @ApiModelProperty(value = "id",required = true)
    private Long id;

    @ApiModelProperty(value = "类型",required = true)
    private String type;

    @ApiModelProperty(value = "数量",required = true)
    private int qty;
}
