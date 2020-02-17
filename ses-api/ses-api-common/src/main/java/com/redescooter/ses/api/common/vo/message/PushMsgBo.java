package com.redescooter.ses.api.common.vo.message;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName:PushMsgBo
 * @description: PushMsgBo
 * @author: Alex
 * @Version：1.3
 * @create: 2019/10/25 16:24
 */
@ApiModel(value = "消息数据传输对象", description = "消息数据传输对象")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class PushMsgBo {

    @ApiModelProperty(value = "enter")
    private GeneralEnter enter;

    @ApiModelProperty(value = "业务状态")
    private String status;

    @ApiModelProperty(value = "消息提示中的参数")
    private Object[] args;

    @ApiModelProperty(value = "业务类型")
    private String bizType;

    @ApiModelProperty(value = "业务Id")
    private Long bizId;

    @ApiModelProperty(value = "拥有者的UserId")
    private Long belongId;

    @ApiModelProperty(value = "appId")
    private String appId;

    @ApiModelProperty(value = "系统Id")
    private String systemId;

    @ApiModelProperty(value = "推送类型 PC、android")
    private String pushType;

    @ApiModelProperty(value = "消息类型站内、站外 、空 默认 空 ")
    private String mesageType;

    @ApiModelProperty(value = "消息优先级 0 无需提醒 1 小红点 2 强提醒")
    private String messagePriority;

    @ApiModelProperty(value = "服务类型（只在维修系统中有使用） 可以为空")
    private String serviceType;

    @ApiModelProperty(value = "当前业务的 租户Id")
    private String inputTenantId;
}
