package com.redescooter.ses.mobile.rps.vo.restproductionorder;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.*;

import java.util.List;

import io.swagger.annotations.*;

/**
 * @ClassName:ProductQcTempleteItemResult
 * @description: ProductQcTempleteItemResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/11/02 19:13 
 */
@ApiModel(value = "", description = "")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
public class ProductQcTempleteItemResult extends GeneralResult {


    @ApiModelProperty(value = "模版Id")
    private Long id;

    @ApiModelProperty(value = "质检项条目Id")
    private String itemName;

    @ApiModelProperty(value = "子单据Id")
    private Long orderBId;

    @ApiModelProperty(value = "质检项结果集")
    private List<ProductQcTempleteResultResult> qcTempleteList;
}
