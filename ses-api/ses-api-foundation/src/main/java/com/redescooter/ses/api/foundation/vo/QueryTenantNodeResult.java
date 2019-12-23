package com.redescooter.ses.api.foundation.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @ClassName:QueryTenantNdoeResult
 * @description: QueryTenantNdoeResult
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/24 01:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "租户节点", description = "租户节点")
public class QueryTenantNodeResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "租户主键")
    private Long tenantId;

    @ApiModelProperty(value = "事件")
    /**
     * 事件
     */
    private String event;

    /**
     * 事件时间
     */
    @ApiModelProperty(value = "时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "UTC")
    private Date eventTime;

    @ApiModelProperty(value = "备注")
    /**
     * 备注
     */
    private String memo;

    @ApiModelProperty(value = "创建人")
    private Long createBy;

}
