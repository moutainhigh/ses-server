package com.redescooter.ses.web.website.dm;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
    * 产品种类表
    */
@ApiModel(value="com-redescooter-ses-web-website-dm-SiteProductClass")
@Data
@TableName(value = "site_product_class")
public class SiteProductClass implements Serializable {

    /**
     * 主键 主键
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value="主键 主键")
    private Long id;

    /**
     * 逻辑删除
     */
    @TableField(value = "dr")
    @ApiModelProperty(value="逻辑删除")
    @TableLogic
    private Integer dr;

    /**
     * 状态,1正常，-1失效
     */
    @TableField(value = "`status`")
    @ApiModelProperty(value="状态,1正常，-1失效")
    private String status;

    /**
     * 产品种类名称
     */
    @TableField(value = "product_class_name")
    @ApiModelProperty(value="产品种类名称")
    private String productClassName;

    /**
     * 产品种类编码
     */
    @TableField(value = "product_class_code")
    @ApiModelProperty(value="产品种类编码")
    private String productClassCode;

    /**
     * 中文名称
     */
    @TableField(value = "cn_name")
    @ApiModelProperty(value="中文名称")
    private String cnName;

    /**
     * 法文名称
     */
    @TableField(value = "fr_name")
    @ApiModelProperty(value="法文名称")
    private String frName;

    /**
     * 英文名称
     */
    @TableField(value = "en_name")
    @ApiModelProperty(value="英文名称")
    private String enName;

    /**
     * 备注
     */
    @TableField(value = "remark")
    @ApiModelProperty(value="备注")
    private String remark;

    /**
     * 是否同步
     */
    @TableField(value = "synchronize_flag")
    @ApiModelProperty(value="是否同步")
    private Boolean synchronizeFlag;

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
    private BigDecimal def6;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_DR = "dr";

    public static final String COL_STATUS = "status";

    public static final String COL_PRODUCT_CLASS_NAME = "product_class_name";

    public static final String COL_PRODUCT_CLASS_CODE = "product_class_code";

    public static final String COL_CN_NAME = "cn_name";

    public static final String COL_FR_NAME = "fr_name";

    public static final String COL_EN_NAME = "en_name";

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
