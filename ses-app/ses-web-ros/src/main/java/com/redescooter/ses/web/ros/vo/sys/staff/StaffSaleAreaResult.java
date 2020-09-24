package com.redescooter.ses.web.ros.vo.sys.staff;

import com.redescooter.ses.api.common.vo.tree.TreeNode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassNameStaffSaleAreaResult
 * @Description
 * @Author Aleks
 * @Date2020/9/17 13:34
 * @Version V1.0
 **/
@Data
public class StaffSaleAreaResult extends TreeNode {

    @ApiModelProperty("销售区域的id")
    private Long saleCityId = 0L;

    @ApiModelProperty("销售区域的id")
    private String areaName = "";

    @ApiModelProperty("销售区域的id")
    private String areaCode = "";



}
