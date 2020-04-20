package com.redescooter.ses.mobile.rps.vo.materialqc;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName:PartTemplateEnter
 * @description: PartTemplateEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/13 17:56
 */
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "部件模板质检项", description = "部件模板质检项")
public class PartTemplateEnter extends GeneralEnter {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "图片 多个图片逗号分隔")
    private String pictureList;

    @ApiModelProperty(value = "部件质检结果Id")
    private Long qcResultId;
}
