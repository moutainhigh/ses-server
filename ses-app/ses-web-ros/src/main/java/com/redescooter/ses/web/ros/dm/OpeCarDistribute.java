package com.redescooter.ses.web.ros.dm;

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
import java.util.Date;

/**
 * @author chris
 * @since 2020-12-27
 */
@ApiModel(value = "车辆分配表")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@JsonInclude(value = Include.NON_NULL)
public class OpeCarDistribute implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.UUID)
    private Long id;

    @ApiModelProperty(value = "是否删除 0正常 1删除")
    @TableLogic
    private Integer dr;

    @ApiModelProperty(value = "租户id")
    private Long tenantId;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "客户id")
    private Long customerId;

    @ApiModelProperty(value = "规格类型id")
    private Long specificatTypeId;

    @ApiModelProperty(value = "座位数")
    private Integer seatNumber;

    @ApiModelProperty(value = "VIN Code(相当于SSN)")
    private String vinCode;

    @ApiModelProperty(value = "车牌")
    private String licensePlate;

    @ApiModelProperty(value = "待分配车辆数量")
    private Integer qty;

    @ApiModelProperty(value = "R.SN")
    private String rsn;

    @ApiModelProperty(value = "颜色id")
    private Long colorId;

    @ApiModelProperty(value = "创建人")
    private Long createdBy;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdTime;

    @ApiModelProperty(value = "修改人")
    private Long updatedBy;

    @ApiModelProperty(value = "修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedTime;

    @ApiModelProperty(value = "冗余字段1")
    private String def1;

    @ApiModelProperty(value = "冗余字段2")
    private String def2;

    @ApiModelProperty(value = "冗余字段3")
    private String def3;

    @ApiModelProperty(value = "冗余字段4")
    private String def4;

    @ApiModelProperty(value = "冗余字段5")
    private String def5;

}