package com.redescooter.ses.web.ros.vo.sellsy.enter.document;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.web.ros.exception.ThirdValidationExceptionCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@ApiModel(value = "更新单据状态", description = "更新单据状态")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SellsyUpdateDocumentStatusEnter {

    @ApiModelProperty(value = "单据Id")
    @NotNull(code = ThirdValidationExceptionCode.SELLSY_DOCUMENT_ID_IS_EMTPY, message = "单据Id 为空")
    private int docid;

    @NotNull(code = ThirdValidationExceptionCode.BUSSINESS_OBJ_IS_EMPTY, message = "业务对象为空")
    private SellsyUpdateDocumentInvoidSatusEnter document;
}
