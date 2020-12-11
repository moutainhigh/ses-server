package com.redescooter.ses.admin.dev.vo.scooter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 车辆配件 DTO
 * @author assert
 * @date 2020/12/8 19:48
 */
@Data
@ApiModel(value = "车辆配件")
public class AdminScooterPartsDTO {

    /**
     * 主键Id
     */
    @ApiModelProperty(value="主键id", dataType = "Long")
    private Long id;

    /**
     * 车辆Id
     */
    @ApiModelProperty(value="车辆Id", dataType = "Long")
    private Long scooterId;

    /**
     * 配件名称
     */
    @ApiModelProperty(value="配件名称", dataType = "String")
    private String name;

    /**
     * 序列号
     */
    @ApiModelProperty(value = "序列号", dataType = "String")
    private String sn;

    /**
     * 数量
     */
    @ApiModelProperty(value="数量", dataType = "Integer")
    private Integer qty;

    /**
     * 创建人
     */
    @ApiModelProperty(value="创建人", dataType = "Long")
    private Long createdBy;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间", dataType = "Date")
    private Date createdTime;

    /**
     * 更新人
     */
    @ApiModelProperty(value="更新人", dataType = "Long")
    private Long updatedBy;

    /**
     * 更新时间
     */
    @ApiModelProperty(value="更新时间", dataType = "Date")
    private Date updatedTime;

}
