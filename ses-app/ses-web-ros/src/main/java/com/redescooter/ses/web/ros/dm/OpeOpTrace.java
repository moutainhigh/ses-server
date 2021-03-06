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
 * 单据操作记录表
 */
@ApiModel(value = "com-redescooter-ses-web-ros-dm-OpeOpTrace")
@Data
@TableName(value = "ope_op_trace")
public class OpeOpTrace {
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
     * 关联的单据id
     */
    @TableField(value = "relation_id")
    @ApiModelProperty(value = "关联的单据id")
    private Long relationId;

    /**
     * 单据类型，1：调拨单，2：采购单，3：发货单，4：出库单，5：委托单
     */
    @TableField(value = "order_type")
    @ApiModelProperty(value = "单据类型，1：调拨单，2：采购单，3：发货单，4：出库单，5：委托单")
    private Integer orderType;

    /**
     * 操作类型，1：创建，2：编辑，3：下单，4：删除，5：取消订单，6：关闭订单，7：备货，8：装车，9：开始质检，10：提交出库，11：发货，12：签收
     */
    @TableField(value = "op_type")
    @ApiModelProperty(value = "操作类型，1：创建，2：编辑，3：下单，4：删除，5：取消订单，6：关闭订单，7：备货，8：装车，9：开始质检，10：提交出库，11：发货，12：签收,13：准备质检，14：确认入库")
    private Integer opType;

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

    public static final String COL_RELATION_ID = "relation_id";

    public static final String COL_ORDER_TYPE = "order_type";

    public static final String COL_OP_TYPE = "op_type";

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