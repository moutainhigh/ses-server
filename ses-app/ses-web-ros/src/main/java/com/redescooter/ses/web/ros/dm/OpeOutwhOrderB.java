package com.redescooter.ses.web.ros.dm;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 中国生产仓库出库单子表
 */
@ApiModel(value = "com-redescooter-ses-web-ros-dm-OpeOutwhOrderB")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "ope_outwh_order_b")
public class OpeOutwhOrderB implements Serializable {
    /**
     * 主键 主键
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "主键 主键")
    private Long id;

    /**
     * 逻辑删除标识 逻辑删除标识
     */
    @TableField(value = "dr")
    @ApiModelProperty(value = "逻辑删除标识 逻辑删除标识")
    @TableLogic
    private Integer dr;

    /**
     * 出库单id
     */
    @TableField(value = "outwh_order_id")
    @ApiModelProperty(value = "出库单id")
    private Long outwhOrderId;

    /**
     * 库存Id
     */
    @TableField(value = "stock_id")
    @ApiModelProperty(value = "库存Id")
    private Long stockId;

    /**
     * 部件产品Id 和 下面的来源一起使用
     */
    @TableField(value = "part_product_id")
    @ApiModelProperty(value = "部件产品Id 和 下面的来源一起使用")
    private Long partProductId;

    /**
     * 产品类型 
     */
    @TableField(value = "product_type")
    @ApiModelProperty(value = "产品类型 ")
    private String productType;

    /**
     * 总数量
     */
    @TableField(value = "total_count")
    @ApiModelProperty(value = "总数量")
    private Integer totalCount;

    /**
     * 剩余出货数量
     */
    @TableField(value = "last_out_count")
    @ApiModelProperty(value = "剩余出货数量")
    private Integer lastOutCount;

    /**
     * 状态
     */
    @TableField(value = "`status`")
    @ApiModelProperty(value = "状态")
    private String status;

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
     * 乐观锁
     */
    @TableField(value = "revision")
    @ApiModelProperty(value = "乐观锁")
    private Integer revision;

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
    @TableField(value = "def5")
    @ApiModelProperty(value = "冗余字段")
    private String def5;

    /**
     * 冗余字段
     */
    @TableField(value = "def6")
    @ApiModelProperty(value = "冗余字段")
    private Double def6;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_DR = "dr";

    public static final String COL_OUTWH_ORDER_ID = "outwh_order_id";

    public static final String COL_STOCK_ID = "stock_id";

    public static final String COL_PART_PRODUCT_ID = "part_product_id";

    public static final String COL_PRODUCT_TYPE = "product_type";

    public static final String COL_TOTAL_COUNT = "total_count";

    public static final String COL_LAST_OUT_COUNT = "last_out_count";

    public static final String COL_STATUS = "status";

    public static final String COL_CREATED_BY = "created_by";

    public static final String COL_CREATED_TIME = "created_time";

    public static final String COL_UPDATED_BY = "updated_by";

    public static final String COL_UPDATED_TIME = "updated_time";

    public static final String COL_REVISION = "revision";

    public static final String COL_DEF1 = "def1";

    public static final String COL_DEF2 = "def2";

    public static final String COL_DEF3 = "def3";

    public static final String COL_DEF5 = "def5";

    public static final String COL_DEF6 = "def6";
}