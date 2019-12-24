package com.redescooter.ses.web.ros.vo.account;

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

/**
 * @ClassName:AccountNodeResult
 * @description: AccountNodeResult
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/24 01:28
 */
@ApiModel(value = "账户节点出参", description = "账户节点出参")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class AccountNodeResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "时间")
    private String event;

    @ApiModelProperty(value = "时间时间")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="UTC")
    private String eventTime;

    @ApiModelProperty(value = "创建人Id")
    private Long createdBy;

    @ApiModelProperty(value = "创建人姓名")
    private String createdFirstName;

    @ApiModelProperty(value = "创建人姓名")
    private String createdLastName;
}
