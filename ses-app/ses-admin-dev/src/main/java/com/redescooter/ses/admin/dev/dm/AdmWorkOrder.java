package com.redescooter.ses.admin.dev.dm;

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
    * OMS工单表
    */
@ApiModel(value="com-redescooter-ses-admin-dev-dm-AdmWorkOrder")
@Data
@TableName(value = "adm_work_order")
public class AdmWorkOrder {
    /**
     * ID
     */
    @TableId(value = "id")
    @ApiModelProperty(value="ID")
    private Long id;

    /**
     * 逻辑删除标识 0正常 1删除
     */
    @TableField(value = "dr")
    @ApiModelProperty(value="逻辑删除标识 0正常 1删除")
    @TableLogic
    private Integer dr;

    /**
     * 工单编号
     */
    @TableField(value = "order_no")
    @ApiModelProperty(value="工单编号")
    private String orderNo;

    /**
     * 工单来源 1：APP，2：ROS，3：SAAS，4：OMS，5：RPS
     */
    @TableField(value = "source")
    @ApiModelProperty(value="工单来源 1：APP，2：ROS，3：SAAS，4：OMS，5：RPS")
    private Integer source;

    /**
     * 工单状态 1：新建（Pending），2：处理中（In Progress），3：已完成（Completed），4：关闭（Closed）
     */
    @TableField(value = "work_order_status")
    @ApiModelProperty(value="工单状态 1：新建（Pending），2：处理中（In Progress），3：已完成（Completed），4：关闭（Closed）")
    private Integer workOrderStatus;

    /**
     * 工单标题
     */
    @TableField(value = "title")
    @ApiModelProperty(value="工单标题")
    private String title;

    /**
     * 联系的邮箱
     */
    @TableField(value = "contact_email")
    @ApiModelProperty(value="联系的邮箱")
    private String contactEmail;

    /**
     * 备注
     */
    @TableField(value = "remark")
    @ApiModelProperty(value="备注")
    private String remark;

    /**
     * 附件1
     */
    @TableField(value = "annex_picture_1")
    @ApiModelProperty(value="附件1")
    private String annexPicture1;

    /**
     * 附件2
     */
    @TableField(value = "annex_picture_2")
    @ApiModelProperty(value="附件2")
    private String annexPicture2;

    /**
     * 附件3
     */
    @TableField(value = "annex_picture_3")
    @ApiModelProperty(value="附件3")
    private String annexPicture3;

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

    public static final String COL_ORDER_NO = "order_no";

    public static final String COL_SOURCE = "source";

    public static final String COL_WORK_ORDER_STATUS = "work_order_status";

    public static final String COL_TITLE = "title";

    public static final String COL_CONTACT_EMAIL = "contact_email";

    public static final String COL_REMARK = "remark";

    public static final String COL_ANNEX_PICTURE_1 = "annex_picture_1";

    public static final String COL_ANNEX_PICTURE_2 = "annex_picture_2";

    public static final String COL_ANNEX_PICTURE_3 = "annex_picture_3";

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