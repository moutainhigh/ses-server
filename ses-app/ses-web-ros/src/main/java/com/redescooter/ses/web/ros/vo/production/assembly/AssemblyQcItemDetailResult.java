package com.redescooter.ses.web.ros.vo.production.assembly;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @ClassName:AssemblyQcItemDetailResult
 * @description: AssemblyQcItemDetailResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/30 15:36
 */
@ApiModel(value = "质检条目详情", description = "质检条目详情")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class AssemblyQcItemDetailResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "状态")
    private String qcStatus;

    @ApiModelProperty(value = "质检日期")
    private Date qcDate;

    @ApiModelProperty(value = "质检人Id")
    private Long qcOperatorId;

    @ApiModelProperty(value = "质检人")
    private String qcOperatorFirstName;

    @ApiModelProperty(value = "质检人")
    private String qcOperatorLastName;

    @ApiModelProperty(value = "质检人")
    private String qcOperatorFullName;
}
