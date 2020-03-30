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
 * @ClassName:AssemblyQcItemResult
 * @description: AssemblyQcItemResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/30 15:31
 */
@ApiModel(value = "Qc质检条目", description = "Qc质检条目")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class AssemblyQcItemResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "序列号")
    private String serialN;

    @ApiModelProperty(value = "状态")
    private String qcStatus;

    @ApiModelProperty(value = "质检时间")
    private Date qcDate;
}
