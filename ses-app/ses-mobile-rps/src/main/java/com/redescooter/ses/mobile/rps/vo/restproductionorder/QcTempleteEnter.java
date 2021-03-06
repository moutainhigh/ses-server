package com.redescooter.ses.mobile.rps.vo.restproductionorder;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import lombok.*;

import io.swagger.annotations.*;

/**
 * @ClassName:QcTempleteEnter
 * @description: QcTempleteEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/11/02 14:51 
 */
@ApiModel(value = "质检模版", description = "质检模版")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
public class QcTempleteEnter extends GeneralEnter {

    @ApiModelProperty(value = "子单据Id", dataType = "Long", required = true)
    private Long id;

    @ApiModelProperty(value = "产品类型 1车辆 2组装件 3部件", dataType = "Integer", required = true)
    private Integer productType;
}
