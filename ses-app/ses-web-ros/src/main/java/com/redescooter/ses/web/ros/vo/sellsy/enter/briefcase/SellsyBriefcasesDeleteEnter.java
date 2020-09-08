package com.redescooter.ses.web.ros.vo.sellsy.enter.briefcase;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.exception.ValidationExceptionCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Data // 生成getter,setter等函数
@AllArgsConstructor // 生成全参数构造函数
@NoArgsConstructor // 生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SellsyBriefcasesDeleteEnter {
    @ApiModelProperty(value = "公文包类型", hidden = true)
    private String filetype = "file";

    @ApiModelProperty(value = "关联Id 指相对应的单据Id")
    @NotNull(code = ValidationExceptionCode.ID_IS_EMPTY, message = "附件Id为空")
    private int fileid;
}
