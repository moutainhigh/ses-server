package com.redescooter.ses.web.ros.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.vo.base.PageResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @ClassName:CustomerListByPageResult
 * @description: CustomerListByPageResult
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/18 11:50
 */
@ApiModel(value = "客户列表出参", description = "客户列表出参")
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class CustomerListByPageResult extends PageResult {
    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "名字")
    private String firstName;

    @ApiModelProperty(value = "名字")
    private String lastName;

    @ApiModelProperty(value = "邮件")
    private String email;

    @ApiModelProperty(value = "cityId")
    private Long cityId;

    @ApiModelProperty(value = "cityName")
    private String cityName;

    @ApiModelProperty(value = "areaId")
    private Long areaId;

    @ApiModelProperty(value = "areaName")
    private String areaName;

    @ApiModelProperty(value = "客户类型")
    private String userType;

    @ApiModelProperty(value = "行业")
    private String industry;

    @ApiModelProperty(value = "车辆数量")
    private Integer scooterQty;

    @ApiModelProperty(value = "客户来源")
    private String source;

    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String createTime;

    @ApiModelProperty(value = "删除人")
    private Long deleteBy;

    @ApiModelProperty(value = "删除时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String deletedTime;
}
