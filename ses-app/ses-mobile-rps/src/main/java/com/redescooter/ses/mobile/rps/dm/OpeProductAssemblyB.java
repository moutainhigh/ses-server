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
 * 产品组装子表
 */
@ApiModel(value = "com-redescooter-ses-mobile-rps-dm-OpeProductAssemblyB")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OpeProductAssemblyB implements Serializable {
    public static final String COL_ID = "id";
    public static final String COL_DR = "dr";
    public static final String COL_PRODUCT_ASSEMBLY_ID = "product_assembly_id";
    public static final String COL_PART_ID = "part_id";
    public static final String COL_QTY = "qty";
    public static final String COL_PART_SERIAL_NUM = "part_serial_num";
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
     * 产品组装表Id
     */
    @ApiModelProperty(value = "产品组装表Id")
    private Long productAssemblyId;

    /**
     * 部件Id
     */
    @ApiModelProperty(value = "部件Id")
    private Long partId;

    /**
     * 部件数量
     */
    @ApiModelProperty(value = "部件数量")
    private Integer qty;

    /**
     * 部件序列号
     */
    @ApiModelProperty(value = "部件序列号")
    private String partSerialNum;

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

    public static OpeProductAssemblyBBuilder builder() {
        return new OpeProductAssemblyBBuilder();
    }
}