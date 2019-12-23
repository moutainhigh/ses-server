package com.redescooter.ses.api.foundation.vo.mail;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 11/10/2019 3:11 下午
 * @ClassName: MailConfigOfTermEnter
 * @Function: TODO
 */
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class MailConfigOfTermEnter extends GeneralEnter {

    /**
     * normal正常，Disabled失效的
     */
    @ApiModelProperty(value="normal正常，Disabled失效的")
    private String status;

    /**
     * 模板编号
     */
    @ApiModelProperty(value="模板编号")
    private Integer mailTemplateNo;

    /**
     * 对应key，不可为空
     */
    @ApiModelProperty(value="对应key，不可为空")
    private String key;
}
