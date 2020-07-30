package com.redescooter.ses.web.ros.vo.customer;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.constant.DateConstant;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 19/12/2019 9:49 上午
 * @ClassName: ListCustomerEnter
 * @Function: TODO
 */
@ApiModel(value = "客户列表分页查询", description = "客户列表分页查询")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class ListCustomerEnter extends PageEnter {

    @ApiModelProperty(value = "城市")
    private String cityName;

    @ApiModelProperty(value = "邮编")
    private String distrustName;

    @ApiModelProperty(value = "垃圾箱")
    private Integer dr;

    @ApiModelProperty(value = "客户类型")
    private String customerType;

    @ApiModelProperty(value = "客户行业类型")
    private String customerIndustry;

    @ApiModelProperty(value = "客户来源类型")
    private String customerSource;

    @ApiModelProperty(value = "客户状态")
    private String status;

    @DateTimeFormat(pattern = DateConstant.DEFAULT_DATETIME_FORMAT)
    @JsonFormat(pattern = DateConstant.DEFAULT_DATETIME_FORMAT, timezone = DateConstant.UTC)
    @ApiModelProperty(value = "创建开始时间")
    private Date createStartDateTime;

    @DateTimeFormat(pattern = DateConstant.DEFAULT_DATETIME_FORMAT)
    @JsonFormat(pattern = DateConstant.DEFAULT_DATETIME_FORMAT, timezone = DateConstant.UTC)
    @ApiModelProperty(value = "创建结束时间")
    private Date createEndDateTime;

    @ApiModelProperty(value = "关键字查询")
    private String keyword;
}
