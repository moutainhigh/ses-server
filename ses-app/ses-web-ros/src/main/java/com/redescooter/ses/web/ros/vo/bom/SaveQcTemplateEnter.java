package com.redescooter.ses.web.ros.vo.bom;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName:SaveQcTemplateEnter
 * @description: SaveQcTemplateEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/07 19:53
 */
@ApiModel(value = "", description = "")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SaveQcTemplateEnter extends GeneralEnter {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "Qc质检模板")
//    private List<QcItemTemplateResult> qcItemTemplateEnterList;
    private String qcItemTemplateEnter;
}
