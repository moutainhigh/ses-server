package com.redescooter.ses.mobile.rps.dm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 部件产品表
 */
@ApiModel(value = "com-redescooter-ses-mobile-rps-dm-OpePartsProduct")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OpePartsProduct implements Serializable {
    public static final String COL_ID = "id";
    public static final String COL_DR = "dr";
    public static final String COL_TENANT_ID = "tenant_id";
    public static final String COL_USER_ID = "user_id";
    public static final String COL_STATUS = "status";
    public static final String COL_SN_CLASS = "sn_class";
    public static final String COL_PRODUCT_TYPE = "product_type";
    public static final String COL_PRODUCT_CODE = "product_code";
    public static final String COL_PRODUCT_NUMBER = "product_number";
    public static final String COL_CN_NAME = "cn_name";
    public static final String COL_FR_NAME = "fr_name";
    public static final String COL_EN_NAME = "en_name";
    public static final String COL_PRODUCTION_CYCLE = "production_cycle";
    public static final String COL_SUM_PARTS_QTY = "sum_parts_qty";
    public static final String COL_MODEL = "model";
    public static final String COL_PICTURES = "pictures";
    public static final String COL_COLOR = "color";
    public static final String COL_AFTER_SALES_FLAG = "after_sales_flag";
    public static final String COL_ADDED_SERVICES_FLAG = "added_services_flag";
    public static final String COL_MATER_PARAMETER = "mater_parameter";
    public static final String COL_OTHER_PARAMETER = "other_parameter";
    public static final String COL_NOTE = "note";
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
     * 主键 主键
     */
    @ApiModelProperty(value = "主键 主键")
    private Long id;

    /**
     * 逻辑删除
     */
    @ApiModelProperty(value = "逻辑删除")
    private Integer dr;

    /**
     * 租户ID
     */
    @ApiModelProperty(value = "租户ID")
    private Long tenantId;

    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID")
    private Long userId;

    /**
     * 状态 up上架，down下架(默认)
     */
    @ApiModelProperty(value = "状态 up上架，down下架(默认)")
    private String status;

    /**
     * 是否可销售,0:SC仅可采购，1:SSC可销售可采购
     */
    @ApiModelProperty(value = "是否可销售,0:SC仅可采购，1:SSC可销售可采购")
    private String snClass;

    /**
     * 产品类型 如1整车，2组装套件，电池
     */
    @ApiModelProperty(value = "产品类型 如1整车，2组装套件，电池")
    private Integer productType;

    /**
     * 产品编码
     */
    @ApiModelProperty(value = "产品编码")
    private String productCode;

    /**
     * 产品编号
     */
    @ApiModelProperty(value = "产品编号")
    private String productNumber;

    /**
     * 中文名称
     */
    @ApiModelProperty(value = "中文名称")
    private String cnName;

    /**
     * 法文名称
     */
    @ApiModelProperty(value = "法文名称")
    private String frName;

    /**
     * 英文名称
     */
    @ApiModelProperty(value = "英文名称")
    private String enName;

    /**
     * 生产周期
     */
    @ApiModelProperty(value = "生产周期")
    private String productionCycle;

    /**
     * 已选部品数量
     */
    @ApiModelProperty(value = "已选部品数量")
    private Integer sumPartsQty;

    /**
     * 型号
     */
    @ApiModelProperty(value = "型号")
    private String model;

    /**
     * 图片
     */
    @ApiModelProperty(value = "图片")
    private String pictures;

    /**
     * 颜色
     */
    @ApiModelProperty(value = "颜色")
    private String color;

    /**
     * 是否支持售后服务
     */
    @ApiModelProperty(value = "是否支持售后服务")
    private Boolean afterSalesFlag;

    /**
     * 是否支持增值服务
     */
    @ApiModelProperty(value = "是否支持增值服务")
    private Boolean addedServicesFlag;

    /**
     * 产品参数 存储JSON
     */
    @ApiModelProperty(value = "产品参数 存储JSON")
    private String materParameter;

    /**
     * 其他参数 存储JSON
     */
    @ApiModelProperty(value = "其他参数 存储JSON")
    private String otherParameter;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String note;

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
    private BigDecimal def6;

    private static final long serialVersionUID = 1L;

    public static OpePartsProductBuilder builder() {
        return new OpePartsProductBuilder();
    }
}