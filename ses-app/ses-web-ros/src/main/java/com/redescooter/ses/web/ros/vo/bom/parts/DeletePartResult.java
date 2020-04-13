package com.redescooter.ses.web.ros.vo.bom.parts;

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
 * @ClassName:DeletePartResult
 * @description: DeletePartResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/08 16:42
 */
@ApiModel(value = "删除部件相关联产品", description = "删除部件相关联产品")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class DeletePartResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "名字")
    private String cnName;

    @ApiModelProperty(value = "名字")
    private String enName;

    @ApiModelProperty(value = "部品号")
    private String partsN;

    @ApiModelProperty(value = "车辆绑定产品")
    private List<DeletePartBindProductResult> scooterList;

    @ApiModelProperty(value = "组合绑定产品")
    private List<DeletePartBindProductResult> combinationList;
}
