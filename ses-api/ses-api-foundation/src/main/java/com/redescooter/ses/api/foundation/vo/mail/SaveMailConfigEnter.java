package com.redescooter.ses.api.foundation.vo.mail;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.foundation.exception.ValidationExceptionCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 11/10/2019 3:15 下午
 * @ClassName: SaveMailConfigEnter
 * @Function: TODO
 */
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class SaveMailConfigEnter extends GeneralEnter {
    /**
     * 主键
     */
    @ApiModelProperty(value = "模板参数主键，更新时传值，创建时不传")
    private Long id;

    /**
     * 模板编号
     */
    @ApiModelProperty(value = "模板编号",required = true)
    @NotNull(code = ValidationExceptionCode.MAILTEMPLATE_IS_NOT_EMPTY, message = "邮件模板编号不能为空")
    private Integer mailTemplateNo;

    /**
     * 应用ID
     */
    @ApiModelProperty(value = "应用ID",required = true)
    @NotNull(code = ValidationExceptionCode.APPID_IS_NOT_EMPTY, message = "appid不能为空")
    private String mailAppId;

    /**
     * 对应key，不可为空
     */
    @ApiModelProperty(value = "对应key，不可为空",required = true)
    @NotNull(code = ValidationExceptionCode.KEY_IS_EMPTY, message = "key值不能为空")
    private String paramKey;

    /**
     * 对应值域，可为空
     */
    @ApiModelProperty(value = "对应值域，可为空")
    private String paramValue;
}
