package com.redescooter.ses.web.ros.dm;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
    * 交货方式
    */
@ApiModel(value="ses-redescooter-com-ros-dm-OpeDeliveryOption")
@Data
@TableName(value = "ope_delivery_option")
public class OpeDeliveryOption implements Serializable {

    /**
     * 主建
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value="主建")
    private Long id;

    /**
     * 删除标识【0正常，1删除】
     */
    @TableField(value = "dr")
    @ApiModelProperty(value="删除标识【0正常，1删除】")
    @TableLogic
    private Integer dr;

    /**
     * 租户ID
     */
    @TableField(value = "tenant_id")
    @ApiModelProperty(value="租户ID")
    private Long tenantId;

    /**
     * 用户ID
     */
    @TableField(value = "user_id")
    @ApiModelProperty(value="用户ID")
    private Long userId;

    /**
     * 状态标识【0正常，1失效】
     */
    @TableField(value = "`status`")
    @ApiModelProperty(value="状态标识【0正常，1失效】")
    private Integer status;

    /**
     * 交货方式编码
     */
    @TableField(value = "option_code")
    @ApiModelProperty(value="交货方式编码")
    private String optionCode;

    /**
     * 交货名称
     */
    @TableField(value = "option_neme")
    @ApiModelProperty(value="交货名称")
    private String optionNeme;

    @TableField(value = "price")
    @ApiModelProperty(value="")
    private Double price;

    /**
     * 备注说明
     */
    @TableField(value = "memo")
    @ApiModelProperty(value="备注说明")
    private String memo;

    /**
     * 创建人
     */
    @TableField(value = "created_by")
    @ApiModelProperty(value="创建人")
    private Long createdBy;

    @TableField(value = "created_time")
    @ApiModelProperty(value="")
    private Date createdTime;

    /**
     * 更新人
     */
    @TableField(value = "updated_by")
    @ApiModelProperty(value="更新人")
    private Long updatedBy;

    @TableField(value = "updated_time")
    @ApiModelProperty(value="")
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
    @TableField(value = "def5")
    @ApiModelProperty(value="冗余字段")
    private String def5;

    /**
     * 冗余字段
     */
    @TableField(value = "def6")
    @ApiModelProperty(value="冗余字段")
    private Double def6;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_DR = "dr";

    public static final String COL_TENANT_ID = "tenant_id";

    public static final String COL_USER_ID = "user_id";

    public static final String COL_STATUS = "status";

    public static final String COL_OPTION_CODE = "option_code";

    public static final String COL_OPTION_NEME = "option_neme";

    public static final String COL_PRICE = "price";

    public static final String COL_MEMO = "memo";

    public static final String COL_CREATED_BY = "created_by";

    public static final String COL_CREATED_TIME = "created_time";

    public static final String COL_UPDATED_BY = "updated_by";

    public static final String COL_UPDATED_TIME = "updated_time";

    public static final String COL_DEF1 = "def1";

    public static final String COL_DEF2 = "def2";

    public static final String COL_DEF3 = "def3";

    public static final String COL_DEF5 = "def5";

    public static final String COL_DEF6 = "def6";
}