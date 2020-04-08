package com.redescooter.ses.web.ros.vo.bom;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName:QcItemTemplateEnter
 * @description: QcItemTemplateEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/07 19:54
 */
@ApiModel(value = "质检项入参", description = "质检项入参")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class QcItemTemplateResult extends GeneralEnter {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "质检名称")
    private String qcitemName;

    @ApiModelProperty(value = "质检项结果")
    private List<QcResultEnter> qcResuleEnterList;
}
