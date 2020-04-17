package com.redescooter.ses.mobile.rps.vo.materialqc;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

import io.swagger.annotations.*;

/**
 * @ClassName:MaterialQcTemplateDetailResult
 * @description: MaterialQcTemplateDetailResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/13 17:12
 */
@ApiModel(value = "部品质检模板入参", description = "部品质检模板入参")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class MaterialQcTemplateDetailResult extends GeneralResult {

    @ApiModelProperty(value = "采购单子表Id")
    private Long id;

    @ApiModelProperty(value = "部件Id")
    private Long partId;

    @ApiModelProperty(value = "部件名称")
    private String partCnName;

    @ApiModelProperty(value = "部件编号")
    private String partN;

    @ApiModelProperty(value = "质检项")
    private List<PartTemplateResult> partTemplateList;
}
