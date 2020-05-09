package com.redescooter.ses.mobile.rps.vo.materialqc;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:CheckPartQcBySerilaNEnter
 * @description: CheckPartQcBySerilaNEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/05/09 19:00
 */
@ApiModel(value = "检查是否质检成功过入参", description = "检查是否质检成功过入参")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class CheckPartQcBySerilaNEnter extends GeneralEnter {

    @ApiModelProperty(value = "订单")
    private Long id;

    @ApiModelProperty(value = "部品Id")
    private Long partId;

    @ApiModelProperty(value = "序列号")
    private String serialN;
}
