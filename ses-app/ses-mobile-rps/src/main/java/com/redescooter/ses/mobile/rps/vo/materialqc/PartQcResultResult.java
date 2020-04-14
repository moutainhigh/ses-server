package com.redescooter.ses.mobile.rps.vo.materialqc;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:PartQcResultResult
 * @description: PartQcResultResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/13 17:18
 */
@ApiModel(value = "部件模板结果", description = "部件模板结果")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class PartQcResultResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "模板质检项Id")
    private Long partQcTemplateId;

    @ApiModelProperty(value = "质检结果")
    private String qcResult;

    @ApiModelProperty(value = "上传图片标志")
    private Boolean uploadFlag;

    @ApiModelProperty(value = "结果排序级别")
    private int resultsSequence;
}
