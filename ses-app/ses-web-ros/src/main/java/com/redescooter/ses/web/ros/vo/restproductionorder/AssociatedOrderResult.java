package com.redescooter.ses.web.ros.vo.restproductionorder;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import lombok.*;

import java.util.Date;

import io.swagger.annotations.*;

/**
 * @ClassName:AssociatedOrder
 * @description: AssociatedOrder
 * @author: Alex
 * @Version：1.3
 * @create: 2020/11/10 15:02 
 */
@ApiModel(value = "关联单据出参", description = "关联单据出参")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
public class AssociatedOrderResult extends GeneralEnter {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "订单类型")
    private Integer orderType;

    @ApiModelProperty(value = "订单编号")
    private String orderNo;

    @ApiModelProperty(value = "创建时间")
    private Date createDate;

}
