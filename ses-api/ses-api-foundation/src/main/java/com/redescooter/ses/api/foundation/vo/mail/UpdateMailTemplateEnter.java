package com.redescooter.ses.api.foundation.vo.mail;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 11/10/2019 3:17 下午
 * @ClassName: SaveMailTemplateEnter
 * @Function: TODO
 */
@Data //生成getter,setter等函数
public class UpdateMailTemplateEnter extends SaveMailTemplateEnter {
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;

}
