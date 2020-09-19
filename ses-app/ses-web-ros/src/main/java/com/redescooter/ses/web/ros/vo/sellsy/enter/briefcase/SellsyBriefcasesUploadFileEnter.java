package com.redescooter.ses.web.ros.vo.sellsy.enter.briefcase;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.exception.ValidationExceptionCode;
import com.redescooter.ses.web.ros.enums.sellsy.SellsyBriefcaseTypeEnums;
import com.redescooter.ses.web.ros.exception.ThirdValidationExceptionCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Data // 生成getter,setter等函数
@AllArgsConstructor // 生成全参数构造函数
@NoArgsConstructor // 生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SellsyBriefcasesUploadFileEnter {
    @ApiModelProperty(value = "公文包类型")
    @NotNull(code = ThirdValidationExceptionCode.SELLSY_BRIEFCASES_LINKEDTYPE_IS_EMPTY, message = "附件类型为空")
    private SellsyBriefcaseTypeEnums linkedtype = SellsyBriefcaseTypeEnums.invoice;

    @ApiModelProperty(value = "关联Id 指相对应的单据Id")
    @NotNull(code = ValidationExceptionCode.ID_IS_EMPTY, message = "附件Id为空")
    private int linkedid;

    @ApiModelProperty(value = "文件地址 oss 下载地址")
    private String fileUrl;
}
