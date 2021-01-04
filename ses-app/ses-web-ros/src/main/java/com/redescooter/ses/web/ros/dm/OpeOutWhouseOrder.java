package com.redescooter.ses.web.ros.dm;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 出库单
 */
@ApiModel(value = "com-redescooter-ses-web-ros-dm-OpeOutWhouseOrder")
@Data
@TableName(value = "ope_out_whouse_order")
public class OpeOutWhouseOrder {
    private static final long serialVersionUID = 1L;
    /**
     * 主键id
     */
    @TableId(value = "id")
    @ApiModelProperty(value = "主键id")
    private Long id;

    /**
     * 逻辑删除
     */
    @TableField(value = "dr")
    @ApiModelProperty(value = "逻辑删除")
    @TableLogic
    private Integer dr;

    /**
     * 租户ID
     */
    @TableField(value = "tenant_id")
    @ApiModelProperty(value = "租户ID")
    private Long tenantId;

    /**
     * 部门id（做数据权限用）
     */
    @TableField(value = "dept_id")
    @ApiModelProperty(value = "部门id（做数据权限用）")
    private Long deptId;

    /**
     * 关联的单据id
     */
    @TableField(value = "relation_id")
    @ApiModelProperty(value = "关联的单据id")
    private Long relationId;

    /**
     * 关联的单据号
     */
    @TableField(value = "relation_no")
    @ApiModelProperty(value = "关联的单据号")
    private String relationNo;

    /**
     * 关联的单据类型，3：发货单，9：组装单
     */
    @TableField(value = "relation_type")
    @ApiModelProperty(value = "关联的单据类型，3：发货单，9：组装单")
    private Integer relationType;

    /**
     * 入库仓库。1:成品库，2:原料库，3:不合格品库
     */
    @TableField(value = "wh_type")
    @ApiModelProperty(value = "入库仓库。1:成品库，2:原料库，3:不合格品库")
    private Integer whType;

    /**
     * 出库单号
     */
    @TableField(value = "out_wh_no")
    @ApiModelProperty(value = "出库单号")
    private String outWhNo;

    /**
     * 出库单状态，0：待出库，10：质检中，20：已出库，30：已取消
     */
    @TableField(value = "out_wh_status")
    @ApiModelProperty(value = "出库单状态，0：待出库，10：质检中，20：已出库，30：已取消")
    private Integer outWhStatus;

    /**
     * 出库单类型，1：整车，2：组装件，3：部件
     */
    @TableField(value = "out_wh_type")
    @ApiModelProperty(value = "出库单类型，1：整车，2：组装件，3：部件")
    private Integer outWhType;

    /**
     * 出库类型，1：销售调拨，2：补料出库，3：生产组装，4：其它
     */
    @TableField(value = "out_type")
    @ApiModelProperty(value = "出库类型，1：销售调拨，2：补料出库，3：生产组装，4：其它")
    private Integer outType;

    /**
     * 出库数量
     */
    @TableField(value = "out_wh_qty")
    @ApiModelProperty(value = "出库数量")
    private Integer outWhQty;

    /**
     * 已出库数量
     */
    @TableField(value = "already_out_wh_qty")
    @ApiModelProperty(value = "已出库数量")
    private Integer alreadyOutWhQty;

    /**
     * 国家编码如+86
     */
    @TableField(value = "country_code")
    @ApiModelProperty(value = "国家编码如+86")
    private String countryCode;

    /**
     * 联系人电话
     */
    @TableField(value = "telephone")
    @ApiModelProperty(value = "联系人电话")
    private String telephone;

    /**
     * 邮箱
     */
    @TableField(value = "mail")
    @ApiModelProperty(value = "邮箱")
    private String mail;

    /**
     * 备注
     */
    @TableField(value = "remark")
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 创建人
     */
    @TableField(value = "created_by")
    @ApiModelProperty(value = "创建人")
    private Long createdBy;

    /**
     * 创建时间
     */
    @TableField(value = "created_time")
    @ApiModelProperty(value = "创建时间")
    private Date createdTime;

    /**
     * 更新人
     */
    @TableField(value = "updated_by")
    @ApiModelProperty(value = "更新人")
    private Long updatedBy;

    /**
     * 更新时间
     */
    @TableField(value = "updated_time")
    @ApiModelProperty(value = "更新时间")
    private Date updatedTime;

    /**
     * 冗余字段
     */
    @TableField(value = "def1")
    @ApiModelProperty(value = "冗余字段")
    private String def1;

    /**
     * 冗余字段
     */
    @TableField(value = "def2")
    @ApiModelProperty(value = "冗余字段")
    private String def2;

    /**
     * 冗余字段
     */
    @TableField(value = "def3")
    @ApiModelProperty(value = "冗余字段")
    private String def3;

    /**
     * 冗余字段
     */
    @TableField(value = "def4")
    @ApiModelProperty(value = "冗余字段")
    private String def4;

    /**
     * 冗余字段
     */
    @TableField(value = "def5")
    @ApiModelProperty(value = "冗余字段")
    private BigDecimal def5;

    public static final String COL_ID = "id";

    public static final String COL_DR = "dr";

    public static final String COL_TENANT_ID = "tenant_id";

    public static final String COL_DEPT_ID = "dept_id";

    public static final String COL_RELATION_ID = "relation_id";

    public static final String COL_RELATION_NO = "relation_no";

    public static final String COL_RELATION_TYPE = "relation_type";

    public static final String COL_WH_TYPE = "wh_type";

    public static final String COL_OUT_WH_NO = "out_wh_no";

    public static final String COL_OUT_WH_STATUS = "out_wh_status";

    public static final String COL_OUT_WH_TYPE = "out_wh_type";

    public static final String COL_OUT_TYPE = "out_type";

    public static final String COL_OUT_WH_QTY = "out_wh_qty";

    public static final String COL_ALREADY_OUT_WH_QTY = "already_out_wh_qty";

    public static final String COL_COUNTRY_CODE = "country_code";

    public static final String COL_TELEPHONE = "telephone";

    public static final String COL_MAIL = "mail";

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
