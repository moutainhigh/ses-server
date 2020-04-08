package com.redescooter.ses.web.ros.vo.bom.parts;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:DeletePartBindProductResult
 * @description: DeletePartBindProductResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/08 16:45
 */
@ApiModel(value = "删除部件已绑定的商品", description = "删除部件已绑定的商品")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class DeletePartBindProductResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "名字")
    private String cnName;

    @ApiModelProperty(value = "名字")
    private String enName;

    @ApiModelProperty(value = "产品编号")
    private String productN;
}
