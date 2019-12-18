package com.redescooter.ses.web.ros.vo;

import com.redescooter.ses.api.common.vo.base.PageEnter;
import lombok.Data;

import io.swagger.annotations.*;

/**
 * @ClassName:CustomerListByPageEnter
 * @description: CustomerListByPageEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/18 11:22
 */
@ApiModel(value = "客户列表入参", description = "客户列表入参")
@Data
public class CustomerListByPageEnter extends PageEnter {

    @ApiModelProperty(value = "城市Id")
    private Long cityId;

    @ApiModelProperty(value = "区域Id")
    private Long areaId;

    @ApiModelProperty(value = "客户类型,企业、个人",allowableValues = "ENTERPRISE,PERSONAL")
    private String userType;

    @ApiModelProperty(value = "行业类型, 餐厅、快递",allowableValues = "RESTAURANT,EXPRESS_DELIVERY")
    private String industry;

    @ApiModelProperty(value = "客户来源,官网、ROS系统",allowableValues = "OFFICIAL_WEBSITE,ROS")
    private String source;

    @ApiModelProperty(value = "开始创建时间")
    private String startCreateTime;

    @ApiModelProperty(value = "结束创建时间")
    private String endCreateTime;

    @ApiModelProperty(value = "模糊查询")
    private String keyword;

}
