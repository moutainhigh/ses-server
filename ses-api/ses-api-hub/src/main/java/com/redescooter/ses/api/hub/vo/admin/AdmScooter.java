package com.redescooter.ses.api.hub.vo.admin;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author chris
 * @since 2021-05-13
 */
@ApiModel(value = "车辆管理表")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@JsonInclude(value = Include.NON_NULL)
public class AdmScooter implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @TableId(value = "id", type = IdType.UUID)
    private Long id;

    @ApiModelProperty(value = "逻辑删除标识 0正常 1删除")
    @TableLogic
    private Integer dr;

    @ApiModelProperty(value = "序列号")
    private String sn;

    @ApiModelProperty(value = "分组id（车型）")
    private Long groupId;

    @ApiModelProperty(value = "颜色id")
    private Long colorId;

    @ApiModelProperty(value = "mac地址")
    private String macAddress;

    @ApiModelProperty(value = "车辆的配置控制器，1：E50，2：E100，3：E125")
    private Integer scooterController;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "创建人")
    private Long createdBy;

    @ApiModelProperty(value = "创建时间", hidden = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdTime;

    @ApiModelProperty(value = "更新人")
    private Long updatedBy;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedTime;

    @ApiModelProperty(value = "冗余字段")
    private String def1;

    @ApiModelProperty(value = "冗余字段")
    private String def2;

    @ApiModelProperty(value = "冗余字段")
    private String def3;

    @ApiModelProperty(value = "冗余字段")
    private String def4;

    @ApiModelProperty(value = "冗余字段")
    private BigDecimal def5;

    @ApiModelProperty(value = "颜色名称")
    private String colorName;

    @ApiModelProperty(value = "色值")
    private String colorValue;

    @ApiModelProperty(value = "分组名称")
    private String groupName;

    @ApiModelProperty(value = "蓝牙名称")
    private String macName;
}