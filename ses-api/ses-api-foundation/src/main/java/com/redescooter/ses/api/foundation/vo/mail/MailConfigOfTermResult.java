package com.redescooter.ses.api.foundation.vo.mail;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

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
public class MailConfigOfTermResult extends GeneralResult {
    /**
     * 主键
     */
    @ApiModelProperty(value="主键")
    private Long id;

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
     * 系统ID
     */
    @ApiModelProperty(value="系统ID")
    private String systemId;

    /**
     * 应用ID
     */
    @ApiModelProperty(value="应用ID")
    private String appId;

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


    /**
     * 创建人
     */
    @ApiModelProperty(value="创建人")
    private Long createdBy;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private Date createdTime;
}
