package com.redescooter.ses.web.ros.vo.production.assembly;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName:AssemblyQcInfoResult
 * @description: AssemblyQcInfoResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/30 15:25
 */
@ApiModel(value = "质检详情", description = "质检详情")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class AssemblyQcInfoResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "产品编号")
    private String productN;

    @ApiModelProperty(value = "产品名称")
    private String enName;

    @ApiModelProperty(value = "质检总数")
    private int qcTotal;

    @ApiModelProperty(value = "质检通过数量")
    private int passTotal;

    @ApiModelProperty(value = "是否有之间信息")
    private Boolean hasChildDate;
}
