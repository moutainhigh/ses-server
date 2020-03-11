package com.redescooter.ses.web.ros.vo.tree;

import com.redescooter.ses.api.common.vo.tree.TreeNode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @ClassName SalesAreaTressResult
 * @Author Jerry
 * @date 2020/03/11 14:25
 * @Description:
 */
@ApiModel(value = "部品详情")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class SalesAreaTressResult extends TreeNode {

    @ApiModelProperty(value = "区域名称")
    private String name;
    @ApiModelProperty(value = "区域编码")
    private String code;
    @ApiModelProperty(value = "是否选中")
    private Boolean checked;
    @ApiModelProperty(value = "是否禁用")
    private Boolean disabled;

}
