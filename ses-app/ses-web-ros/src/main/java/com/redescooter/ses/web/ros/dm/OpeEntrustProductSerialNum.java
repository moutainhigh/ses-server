package com.redescooter.ses.web.ros.dm;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
    * 委托单产品序列号表
    */
@ApiModel(value="com-redescooter-ses-web-ros-dm-OpeEntrustProductSerialNum")
@Data
@TableName(value = "ope_entrust_product_serial_num")
public class OpeEntrustProductSerialNum {
    /**
     * 主键id
     */
    @TableId(value = "id")
    @ApiModelProperty(value="主键id")
    private Long id;

    /**
     * 逻辑删除
     */
    @TableField(value = "dr")
    @ApiModelProperty(value="逻辑删除")
    @TableLogic
    private Integer dr;

    /**
     * 关联的id
     */
    @TableField(value = "relation_id")
    @ApiModelProperty(value="关联的id")
    private Long relationId;

    /**
     * 关联的类型，1：车辆，2：组装件，3：部件
     */
    @TableField(value = "relation_type")
    @ApiModelProperty(value="关联的类型，1：车辆，2：组装件，3：部件")
    private Integer relationType;

    /**
     * 批次号
     */
    @TableField(value = "lot")
    @ApiModelProperty(value="批次号")
    private String lot;

    /**
     * 序列号
     */
    @TableField(value = "serial_num")
    @ApiModelProperty(value="序列号")
    private String serialNum;

    /**
     * 到货时间
     */
    @TableField(value = "arrival_time")
    @ApiModelProperty(value="到货时间")
    private Date arrivalTime;

    /**
     * 版本号
     */
    @TableField(value = "version")
    @ApiModelProperty(value="版本号")
    private String version;

    /**
     * 备注
     */
    @TableField(value = "remark")
    @ApiModelProperty(value="备注")
    private String remark;

    /**
     * 创建人
     */
    @TableField(value = "created_by")
    @ApiModelProperty(value="创建人")
    private Long createdBy;

    /**
     * 创建时间
     */
    @TableField(value = "created_time")
    @ApiModelProperty(value="创建时间")
    private Date createdTime;

    /**
     * 更新人
     */
    @TableField(value = "updated_by")
    @ApiModelProperty(value="更新人")
    private Long updatedBy;

    /**
     * 更新时间
     */
    @TableField(value = "updated_time")
    @ApiModelProperty(value="更新时间")
    private Date updatedTime;

    /**
     * 冗余字段
     */
    @TableField(value = "def1")
    @ApiModelProperty(value="冗余字段")
    private String def1;

    /**
     * 冗余字段
     */
    @TableField(value = "def2")
    @ApiModelProperty(value="冗余字段")
    private String def2;

    /**
     * 冗余字段
     */
    @TableField(value = "def3")
    @ApiModelProperty(value="冗余字段")
    private String def3;

    /**
     * 冗余字段
     */
    @TableField(value = "def4")
    @ApiModelProperty(value="冗余字段")
    private String def4;

    /**
     * 冗余字段
     */
    @TableField(value = "def5")
    @ApiModelProperty(value="冗余字段")
    private BigDecimal def5;

    public static final String COL_ID = "id";

    public static final String COL_DR = "dr";

    public static final String COL_RELATION_ID = "relation_id";

    public static final String COL_RELATION_TYPE = "relation_type";

    public static final String COL_LOT = "lot";

    public static final String COL_SERIAL_NUM = "serial_num";

    public static final String COL_ARRIVAL_TIME = "arrival_time";

    public static final String COL_VERSION = "version";

    public static final String COL_REMARK = "remark";

    public static final String COL_CREATED_BY = "created_by";

    public static final String COL_CREATED_TIME = "created_time";

    public static final String COL_UPDATED_BY = "updated_by";

    public static final String COL_UPDATED_TIME = "updated_time";

    public static final String COL_DEF1 = "def1";

    public static final String COL_DEF2 = "def2";

    public static final String COL_DEF3 = "def3";

    public static final String COL_DEF4 = "def4";

    public static final String COL_DEF5 = "def5";
}