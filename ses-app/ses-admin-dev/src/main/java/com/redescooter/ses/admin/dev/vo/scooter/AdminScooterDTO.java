package com.redescooter.ses.admin.dev.vo.scooter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 车辆信息 DTO
 * @author assert
 * @date 2020/12/8 19:33
 */
@Data
@ApiModel(value = "车辆信息")
public class AdminScooterDTO {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id", dataType = "Long")
    private Long id;

    /**
     * 序列号
     */
    @ApiModelProperty(value = "平板序列号", dataType = "String")
    private String sn;

    /**
     * 分组id（车型）
     */
    @ApiModelProperty(value="分组id(车型)", dataType = "Long")
    private Long groupId;

    /**
     * 颜色id
     */
    @ApiModelProperty(value="颜色id", dataType = "Long")
    private Long colorId;

    /**
     * 颜色名称
     */
    @ApiModelProperty(value = "颜色名称", dataType = "String")
    private String colorName;

    /**
     * 色值
     */
    @ApiModelProperty(value = "色值", dataType = "String")
    private String colorValue;

    /**
     * mac名称
     */
    @ApiModelProperty(value = "mac名称", dataType = "String")
    private String macName;

    /**
     * mac地址
     */
    @ApiModelProperty(value="mac地址", dataType = "String")
    private String macAddress;

    /**
     * 车辆的配置控制器 1-E25  2-E50  3-E100  4-E125
     */
    @ApiModelProperty(value="车辆的配置控制器(车辆型号) 1-E25  2-E50  3-E100  4-E125", dataType = "Integer")
    private Integer scooterController;

    /**
     * 备注
     */
    @ApiModelProperty(value="备注", dataType = "String")
    private String remark;

    /**
     * 车辆配件集合
     */
    @ApiModelProperty(value = "车辆配件集合(列表查询时无需关注,用于详情展示)", dataType = "List<AdminScooterPartsDTO>")
    private List<AdminScooterPartsDTO> adminScooterPartsList;

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
