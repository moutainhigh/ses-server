package com.redescooter.ses.web.ros.vo.salearea;

import com.redescooter.ses.api.common.vo.tree.TreeNode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassNameSaleCityTreeResult
 * @Description
 * @Author Aleks
 * @Date2020/9/4 10:54
 * @Version V1.0
 **/
@Data
public class SaleCityTreeResult extends TreeNode {

    @ApiModelProperty("销售区域的id")
    private Long saleCityId;

    @ApiModelProperty("销售区域的id")
    private String areaName;

    @ApiModelProperty("销售区域的id")
    private String areaCode;

    @ApiModelProperty("销售区域的id")
    private Integer level;

    @ApiModelProperty(value = "是否选中")
    private boolean checked = false;

}
