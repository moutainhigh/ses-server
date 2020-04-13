package com.redescooter.ses.api.foundation.vo.user;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @ClassName:AccountNodeResult
 * @description: AccountNodeResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/17 15:54
 */
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class QueryAccountNodeDetailResult extends GeneralResult {
    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "逻辑删除标识 0正常 1删除")
    private Integer dr;

    @ApiModelProperty(value = "user表主键")
    private Long userId;

    @ApiModelProperty(value = "租户主键")
    private Long tenantId;

    @ApiModelProperty(value = "事件")
    private String event;

    @ApiModelProperty(value = "事件时间")
    private Date eventTime;

    @ApiModelProperty(value = "备注")
    private String memo;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "创建人")
    private Long createBy;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "更新人")
    private Long updateBy;
}
