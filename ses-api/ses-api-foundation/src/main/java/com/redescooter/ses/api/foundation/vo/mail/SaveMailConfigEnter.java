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
    @ApiModelProperty(value="主键")
    private Long id;

    /**
     * 模板编号
     */
    @ApiModelProperty(value="模板编号")
    private Integer mailTemplateNo;

    /**
     * 系统ID
     */
    @ApiModelProperty(value="系统ID")
    private String mailSystemId;

    /**
     * 应用ID
     */
    @ApiModelProperty(value="应用ID")
    private String mailAppId;

    /**
     * 对应key，不可为空
     */
    @ApiModelProperty(value="对应key，不可为空")
    private String paramKey;

    /**
     * 对应值域，可为空
     */
    @ApiModelProperty(value="对应值域，可为空")
    private String paramValue;
}
