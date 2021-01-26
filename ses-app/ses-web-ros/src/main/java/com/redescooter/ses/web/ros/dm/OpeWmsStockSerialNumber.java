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

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author chris
 * @since 2021-01-25
 */
@ApiModel(value = "库存产品序列号表")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Accessors(chain = true)
@JsonInclude(value = Include.NON_NULL)
public class OpeWmsStockSerialNumber {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.UUID)
    private Long id;

    @ApiModelProperty(value = "逻辑删除")
    @TableLogic
    private Integer dr;

    @ApiModelProperty(value = "租户ID")
    private Long tenantId;

    @ApiModelProperty(value = "部门id（做数据权限用）")
    private Long deptId;

    @ApiModelProperty(value = "产品类型 1车辆 2组装件 3部件")
    private Integer relationType;

    @ApiModelProperty(value = "关联id")
    private Long relationId;

    @ApiModelProperty(value = "仓库类型，1:中国仓库，2:法国仓库")
    private Integer stockType;

    @ApiModelProperty(value = "序列号")
    private String rsn;

    @ApiModelProperty(value = "库存状态 1可继续使用 2已被别人使用")
    private Integer stockStatus;

    @ApiModelProperty(value = "批次号")
    private String lotNum;

    @ApiModelProperty(value = "供应商序列号")
    private String sn;

    @ApiModelProperty(value = "蓝牙mac地址")
    private String bluetoothMacAddress;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "创建人")
    private Long createdBy;

    @ApiModelProperty(value = "创建时间")
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

}