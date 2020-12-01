package com.redescooter.ses.web.ros.vo.restproductionorder;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.*;

import io.swagger.annotations.*;

/**
 *  @author: alex
 *  @Date: 2020/10/28 10:24
 *  @version：V ROS 1.8.3
 *  @Description:
 */
@ApiModel(value = "下拉框员工列表", description = "下拉框员工列表")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class QueryStaffResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "国家代码")
    private String countryCode;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "电话")
    private String telephone;

    @ApiModelProperty(value = "名字")
    private String firstName;

    @ApiModelProperty(value = "名字")
    private String lastName;
}
