package com.redescooter.ses.web.ros.vo.sellsy.enter;

import lombok.*;

import io.swagger.annotations.*;

@ApiModel(value = "分页对象", description = "分页对象")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SellsyCommonPaginationEnter {

    @ApiModelProperty(value = "页码")
    private int nbperpage = 1;

    @ApiModelProperty(value = "页面大小")
    private int pagenum = 10;
}
