package com.redescooter.ses.web.ros.vo.restproductionorder.assembly;

import com.redescooter.ses.api.common.vo.base.PageEnter;
import lombok.*;

import io.swagger.annotations.*;

/**
 * @ClassName:ProductionAssemblyOrderListEnter
 * @description: ProductionAssemblyOrderListEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/11/10 14:32 
 */
@ApiModel(value = "组装单列表", description = "组装单列表")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
public class ProductionAssemblyOrderListEnter extends PageEnter {

    @ApiModelProperty(value = "页面类型 1 车辆 2 组装")
    private Integer classType;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "关键字")
    private String keyword;
}
