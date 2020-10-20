package com.redescooter.ses.web.ros.vo.bom;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.exception.ValidationExceptionBaseCode;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.ros.exception.ValidationExceptionCode;
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
    @NotNull(code = ValidationExceptionBaseCode.ID_IS_EMPTY, message = "Id为空")
    private Long id;

    @ApiModelProperty(value = "产品类型Id")
    @NotNull(code = ValidationExceptionCode.TYPE_IS_EMPTY, message = "TYPE为空")
    private Integer productionProductType;

    @ApiModelProperty(value = "Qc质检模板 参数格式 [{\"id\": 1000003,\"qcItemName\": \"外壳是否破裂\",\"qcResultEnter\": '[{\"result\": \"PASS\",\"uploadPictureFalg\": true,\"resultSequence\": 1}, {\"result\": " +
            "\"NG\",\"uploadPictureFalg\": true,\"resultSequence\": 2}]'}]\n")
    @NotNull(code = ValidationExceptionCode.QC_TEMPLATE_IS_EMPTY, message = "质检模板为空")
//    private List<QcItemTemplateResult> qcItemTemplateEnterList;
    private String qcItemTemplateEnter;
}
