package com.redescooter.ses.web.ros.dm;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value="com-redescooter-ses-web-ros-dm-OpeProductAssembly")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "ope_product_assembly")
public class OpeProductAssembly {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value="主键")
    private Long id;

    /**
     * 删除标识
     */
    @TableField(value = "dr")
    @ApiModelProperty(value="删除标识")
    private Integer dr;

    /**
     * 商品Id
     */
    @TableField(value = "product_id")
    @ApiModelProperty(value="商品Id")
    private Long productId;

    /**
     * 组装单子表id
     */
    @TableField(value = "assembly_b_id")
    @ApiModelProperty(value="组装单子表id")
    private Long assemblyBId;

    /**
     * 组装单id
     */
    @TableField(value = "assembly_id")
    @ApiModelProperty(value="组装单id")
    private Long assemblyId;

    /**
     * 产品名称
     */
    @TableField(value = "product_name")
    @ApiModelProperty(value="产品名称")
    private String productName;

    /**
     * 产品序列号
     */
    @TableField(value = "product_serial_num")
    @ApiModelProperty(value="产品序列号")
    private String productSerialNum;

    /**
     * 产品类型
     */
    @TableField(value = "product_type")
    @ApiModelProperty(value="产品类型")
    private String productType;

    /**
     * 产品编码
     */
    @TableField(value = "product_code")
    @ApiModelProperty(value="产品编码")
    private String productCode;

    /**
     * 生产日期
     */
    @TableField(value = "production_date")
    @ApiModelProperty(value="生产日期")
    private Date productionDate;

    /**
     * 是否打印标识
     */
    @TableField(value = "print_flag")
    @ApiModelProperty(value="是否打印标识")
    private Boolean printFlag;

    /**
     * 乐观锁
     */
    @TableField(value = "revision")
    @ApiModelProperty(value="乐观锁")
    private Integer revision;

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
    @TableField(value = "def5")
    @ApiModelProperty(value="冗余字段")
    private String def5;

    /**
     * 冗余字段
     */
    @TableField(value = "def6")
    @ApiModelProperty(value="冗余字段")
    private Double def6;

    public static final String COL_ID = "id";

    public static final String COL_DR = "dr";

    public static final String COL_PRODUCT_ID = "product_id";

    public static final String COL_ASSEMBLY_B_ID = "assembly_b_id";

    public static final String COL_ASSEMBLY_ID = "assembly_id";

    public static final String COL_PRODUCT_NAME = "product_name";

    public static final String COL_PRODUCT_SERIAL_NUM = "product_serial_num";

    public static final String COL_PRODUCT_TYPE = "product_type";

    public static final String COL_PRODUCT_CODE = "product_code";

    public static final String COL_PRODUCTION_DATE = "production_date";

    public static final String COL_PRINT_FLAG = "print_flag";

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
}