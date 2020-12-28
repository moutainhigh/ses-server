package com.redescooter.ses.web.ros.vo.assign.tobe.result;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description 待分配列表点击分配带出数据客户信息出参
 * @Author Chris
 * @Date 2020/12/28 10:01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "待分配列表点击分配带出数据客户信息出参", description = "待分配列表点击分配带出数据客户信息出参")
public class ToBeAssignDetailCustomerInfoResult extends GeneralResult implements Serializable {

    private static final long serialVersionUID = -6694841476558427194L;

    @ApiModelProperty(value = "客户id")
    private Long customerId;

    @ApiModelProperty(value = "客户名称")
    private String customerFullName;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "电话")
    private String telephone;

    @ApiModelProperty(value = "邮政编码")
    private String postCode;

    @ApiModelProperty(value = "行业类型 1餐厅 2快递")
    private String industryType;

    @ApiModelProperty(value = "客户类型 1企业 2个人")
    private String customerType;

    @ApiModelProperty(value = "联系地址")
    private String address;

}
