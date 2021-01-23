package com.redescooter.ses.web.website.dm;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 产品表
 */
@ApiModel(value = "com-redescooter-ses-web-website-dm-SiteProduct")
@Data
@TableName(value = "site_product")
public class SiteProduct implements Serializable {
    /**
     * 主键 主键
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "主键 主键")
    private Long id;

    /**
     * 逻辑删除
     */
    @TableField(value = "dr")
    @ApiModelProperty(value = "逻辑删除")

    private Integer dr;

    /**
     * 状态 up上架，down下架(默认)
     */
    @TableField(value = "`status`")
    @ApiModelProperty(value = "状态 up上架，down下架(默认)")
    private String status;

    /**
     * 产品类型 如1整车，2组装套件，电池
     */
    @TableField(value = "product_type")
    @ApiModelProperty(value = "产品类型 如1整车，2组装套件，电池")
    private Integer productType;

    /**
     * 产品编码
     */
    @TableField(value = "product_code")
    @ApiModelProperty(value = "产品编码")
    private String productCode;

    /**
     * 中文名称
     */
    @TableField(value = "cn_name")
    @ApiModelProperty(value = "中文名称")
    private String cnName;

    /**
     * 法文名称
     */
    @TableField(value = "fr_name")
    @ApiModelProperty(value = "法文名称")
    private String frName;

    /**
     * 英文名称
     */
    @TableField(value = "en_name")
    @ApiModelProperty(value = "英文名称")
    private String enName;

    /**
     * 产品型号ID
     */
    @TableField(value = "product_model_id")
    @ApiModelProperty(value = "产品型号ID")
    private Long productModelId;

    /**
     * 图片
     */
    @TableField(value = "picture")
    @ApiModelProperty(value = "图片")
    private String picture;

    /**
     * 最少电池数
     */
    @TableField(value = "min_battery_num")
    @ApiModelProperty(value = "最少电池数")
    private Integer minBatteryNum;

    /**
     * 是否支持售后服务
     */
    @TableField(value = "after_sales_flag")
    @ApiModelProperty(value = "是否支持售后服务")
    private Boolean afterSalesFlag;

    /**
     * 是否支持增值服务
     */
    @TableField(value = "added_services_flag")
    @ApiModelProperty(value = "是否支持增值服务")
    private Boolean addedServicesFlag;

    /**
     * 产品参数 存储JSON
     */
    @TableField(value = "mater_parameter")
    @ApiModelProperty(value = "产品参数 存储JSON")
    private String materParameter;

    /**
     * 其他参数 存储JSON
     */
    @TableField(value = "other_parameter")
    @ApiModelProperty(value = "其他参数 存储JSON")
    private String otherParameter;

    /**
     * 速度
     */
    @TableField(value = "speed")
    @ApiModelProperty(value = "速度")
    private String speed;

    /**
     * 功率
     */
    @TableField(value = "`power`")
    @ApiModelProperty(value = "功率")
    private String power;

    /**
     * 续航里程
     */
    @TableField(value = "mileage")
    @ApiModelProperty(value = "续航里程")
    private String mileage;

    /**
     * 充电周期
     */
    @TableField(value = "charge_cycle")
    @ApiModelProperty(value = "充电周期")
    private String chargeCycle;

    /**
     * 备注
     */
    @TableField(value = "remark")
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 是否同步
     */
    @TableField(value = "synchronize_flag")
    @ApiModelProperty(value = "是否同步")
    private Boolean synchronizeFlag;

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
    private BigDecimal def6;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_DR = "dr";

    public static final String COL_STATUS = "status";

    public static final String COL_PRODUCT_TYPE = "product_type";

    public static final String COL_PRODUCT_CODE = "product_code";

    public static final String COL_CN_NAME = "cn_name";

    public static final String COL_FR_NAME = "fr_name";

    public static final String COL_EN_NAME = "en_name";

    public static final String COL_PRODUCT_MODEL_ID = "product_model_id";

    public static final String COL_PICTURE = "picture";

    public static final String COL_MIN_BATTERY_NUM = "min_battery_num";

    public static final String COL_AFTER_SALES_FLAG = "after_sales_flag";

    public static final String COL_ADDED_SERVICES_FLAG = "added_services_flag";

    public static final String COL_MATER_PARAMETER = "mater_parameter";

    public static final String COL_OTHER_PARAMETER = "other_parameter";

    public static final String COL_SPEED = "speed";

    public static final String COL_POWER = "power";

    public static final String COL_MILEAGE = "mileage";

    public static final String COL_CHARGE_CYCLE = "charge_cycle";

    public static final String COL_REMARK = "remark";

    public static final String COL_SYNCHRONIZE_FLAG = "synchronize_flag";

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