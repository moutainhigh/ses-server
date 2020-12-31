package com.redescooter.ses.mobile.rps.vo.restproductionorder;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @ClassName:ProductQcTempleteResult
 * @description: ProductQcTempleteResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/11/02 14:49 
 */
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
public class ProductQcTempleteResultResult extends GeneralResult {

    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "模板质检项Id")
    private Long templateId;

    @ApiModelProperty(value = "质检结果")
    private String qcResult;

    @ApiModelProperty(value = "质检结果",hidden = true)
    private Boolean passFlag;

    @ApiModelProperty(value = "上传图片标志")
    private Boolean uploadFlag;

    @ApiModelProperty(value = "结果排序级别")
    private int resultsSequence;
}
