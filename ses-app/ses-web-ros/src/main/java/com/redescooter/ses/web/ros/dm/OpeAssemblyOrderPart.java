package com.redescooter.ses.web.ros.dm;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "com-redescooter-ses-web-ros-dm-OpeAssemblyOrderPart")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "ope_assembly_order_part")
public class OpeAssemblyOrderPart {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 逻辑删除标识
     */
    @TableField(value = "dr")
    @ApiModelProperty(value = "逻辑删除标识")
    @TableLogic
    private Integer dr;

    /**
     * 库存Id
     */
    @TableField(value = "stock_id")
    @ApiModelProperty(value = "库存Id")
    private Long stockId;

    /**
     * 部件Id
     */
    @TableField(value = "part_id")
    @ApiModelProperty(value = "部件Id")
    private Long partId;

    /**
     * 组装单Id
     */
    @TableField(value = "assembly_id")
    @ApiModelProperty(value = "组装单Id")
    private Long assemblyId;

    /**
     * 消耗部件数量
     */
    @TableField(value = "total_qty")
    @ApiModelProperty(value = "消耗部件数量")
    private Integer totalQty;

    /**
     * 乐观锁
     */
    @TableField(value = "revision")
    @ApiModelProperty(value = "乐观锁")
    private Integer revision;

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
    @TableField(value = "def5")
    @ApiModelProperty(value = "冗余字段")
    private String def5;

    /**
     * 冗余字段
     */
    @TableField(value = "def6")
    @ApiModelProperty(value = "冗余字段")
    private Double def6;

    public static final String COL_ID = "id";

    public static final String COL_DR = "dr";

    public static final String COL_STOCK_ID = "stock_id";

    public static final String COL_PART_ID = "part_id";

    public static final String COL_ASSEMBLY_ID = "assembly_id";

    public static final String COL_TOTAL_QTY = "total_qty";

    public static final String COL_REVISION = "revision";

    public static final String COL_CREATED_BY = "created_by";

    public static final String COL_CREATED_TIME = "created_time";

    public static final String COL_UPDATED_BY = "updated_by";

    public static final String COL_UPDATED_TIME = "updated_time";

    public static final String COL_DEF1 = "def1";

    public static final String COL_DEF2 = "def2";

    public static final String COL_DEF3 = "def3";

    public static final String COL_DEF5 = "def5";

    public static final String COL_DEF6 = "def6";

    public static OpeAssemblyOrderPartBuilder builder() {
        return new OpeAssemblyOrderPartBuilder();
    }
}