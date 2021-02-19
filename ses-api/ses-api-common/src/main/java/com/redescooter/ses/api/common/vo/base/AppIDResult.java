package com.redescooter.ses.api.common.vo.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @Author jerry
 * @Date 2021/2/19 12:00 下午
 * @Description appid 结果集出参
 **/
@ApiModel(value = "结果集出参", description = "结果集出参")
@Data // 生成getter,setter等函数
@AllArgsConstructor // 生成全参数构造函数
@NoArgsConstructor // 生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class AppIDResult extends GeneralResult {

    /**
     * 应用ID
     */
    @ApiModelProperty(value = "应用ID")
    private String appId;
    /**
     * 系统ID
     */
    @ApiModelProperty(value = "系统ID")
    private String systemId;
    /**
     * 应用值
     */
    @ApiModelProperty(value = "应用值")
    private String value;
    /**
     * 备注说明
     */
    @ApiModelProperty(value = "备注说明")
    private String remark;
}
