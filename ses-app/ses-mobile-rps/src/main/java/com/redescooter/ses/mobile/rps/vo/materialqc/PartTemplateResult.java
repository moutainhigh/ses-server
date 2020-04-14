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
 * @ClassName:PartTemplateResult
 * @description: PartTemplateResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/13 17:16
 */
@ApiModel(value = "模板质检项入参", description = "模板质检项入参")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class PartTemplateResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    private String qcItemName;

    @ApiModelProperty(value = "质检项结果集")
    private List<PartQcResultResult> partQcResultList;
}
