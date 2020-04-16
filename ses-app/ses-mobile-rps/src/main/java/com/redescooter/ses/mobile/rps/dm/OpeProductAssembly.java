package com.redescooter.ses.mobile.rps.dm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 产品组装表
 */
@ApiModel(value = "com-redescooter-ses-mobile-rps-dm-OpeProductAssembly")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OpeProductAssembly implements Serializable {
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
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 删除标识
     */
    @ApiModelProperty(value = "删除标识")
    private Integer dr;

    /**
     * 商品Id
     */
    @ApiModelProperty(value = "商品Id")
    private Long productId;

    /**
     * 组装单子表id
     */
    @ApiModelProperty(value = "组装单子表id")
    private Long assemblyBId;

    /**
     * 组装单id
     */
    @ApiModelProperty(value = "组装单id")
    private Long assemblyId;

    /**
     * 产品名称
     */
    @ApiModelProperty(value = "产品名称")
    private String productName;

    /**
     * 产品序列号
     */
    @ApiModelProperty(value = "产品序列号")
    private String productSerialNum;

    /**
     * 产品类型
     */
    @ApiModelProperty(value = "产品类型")
    private String productType;

    /**
     * 产品编码
     */
    @ApiModelProperty(value = "产品编码")
    private String productCode;

    /**
     * 生产日期
     */
    @ApiModelProperty(value = "生产日期")
    private Date productionDate;

    /**
     * 是否打印标识
     */
    @ApiModelProperty(value = "是否打印标识")
    private Boolean printFlag;

    /**
     * 乐观锁
     */
    @ApiModelProperty(value = "乐观锁")
    private Integer revision;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private Long createdBy;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createdTime;

    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    private Long updatedBy;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private Date updatedTime;

    /**
     * 冗余字段
     */
    @ApiModelProperty(value = "冗余字段")
    private String def1;

    /**
     * 冗余字段
     */
    @ApiModelProperty(value = "冗余字段")
    private String def2;

    /**
     * 冗余字段
     */
    @ApiModelProperty(value = "冗余字段")
    private String def3;

    /**
     * 冗余字段
     */
    @ApiModelProperty(value = "冗余字段")
    private String def5;

    /**
     * 冗余字段
     */
    @ApiModelProperty(value = "冗余字段")
    private Double def6;

    private static final long serialVersionUID = 1L;

    public static OpeProductAssemblyBuilder builder() {
        return new OpeProductAssemblyBuilder();
    }
}