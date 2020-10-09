package com.redescooter.ses.web.ros.vo.setting;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.ros.exception.ValidationExceptionCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 18/9/2019 1:23 下午
 * @ClassName: ImportExcelOrderEnter
 * @Function: TODO
 */
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class ImportParameterEnter extends GeneralEnter {

    @ApiModelProperty(value = "Excel地址", required = true)
    @NotNull(code = ValidationExceptionCode.URL_IS_EMPTY, message = "Excel地址 为空")
    private String url;
    @ApiModelProperty(value = "是否包含重复数据")
    private Boolean repeatFlag;
    @ApiModelProperty(value = "是否强制提交")
    private Boolean againSubmit = Boolean.FALSE;
}
