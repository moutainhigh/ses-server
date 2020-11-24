package com.redescooter.ses.web.ros.vo.restproductionorder.assembly;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import lombok.*;

import java.util.Date;

import io.swagger.annotations.*;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @ClassName:SaveAssemblyOrderEnter
 * @description: SaveAssemblyOrderEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/11/11 14:57 
 */
@ApiModel(value = "保存组装单入参", description = "保存组装单入参")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
public class SaveAssemblyOrderEnter extends GeneralEnter {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "组装类型 1 整车 2组装件")
    private Integer combinType;

    @ApiModelProperty(value = "组装开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date combinStartDate;

    @ApiModelProperty(value = "组装结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date combinEndDate;

    @ApiModelProperty(value = "负责人Id")
    private Long principalId;

    @ApiModelProperty(value = "负责人姓名")
    private String principalName;

    @ApiModelProperty(value = "国家区域代码")
    private String countryCode;

    @ApiModelProperty(value = "电话")
    private String telephone;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "产品信息")
    private String st;
}
