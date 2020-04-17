package com.redescooter.ses.mobile.rps.dm;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "com-redescooter-ses-mobile-rps-dm-OpeWhse")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "ope_whse")
public class OpeWhse implements Serializable {
    /**
     * 主键
     */
    @TableField(value = "id")
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 逻辑删除字段
     */
    @TableField(value = "dr")
    @ApiModelProperty(value = "逻辑删除字段")
    private Integer dr;

    /**
     * 分类名称
     */
    @TableField(value = "name")
    @ApiModelProperty(value = "分类名称")
    private String name;

    /**
     * 分类编码
     */
    @TableField(value = "code")
    @ApiModelProperty(value = "分类编码")
    private String code;

    /**
     * 产品范围
     */
    @TableField(value = "product_range")
    @ApiModelProperty(value = "产品范围")
    private String productRange;

    /**
     * 仓库类型，1采购，2生产，3销售
     */
    @TableField(value = "type")
    @ApiModelProperty(value = "仓库类型，1采购，2生产，3销售")
    private String type;

    /**
     * 备注
     */
    @TableField(value = "remark")
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 国家
     */
    @TableField(value = "country_id")
    @ApiModelProperty(value = "国家")
    private String countryId;

    /**
     * 城市id
     */
    @TableField(value = "city_id")
    @ApiModelProperty(value = "城市id")
    private String cityId;

    /**
     * 区域id
     */
    @TableField(value = "area_id")
    @ApiModelProperty(value = "区域id")
    private Integer areaId;

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

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_DR = "dr";

    public static final String COL_NAME = "name";

    public static final String COL_CODE = "code";

    public static final String COL_PRODUCT_RANGE = "product_range";

    public static final String COL_TYPE = "type";

    public static final String COL_REMARK = "remark";

    public static final String COL_COUNTRY_ID = "country_id";

    public static final String COL_CITY_ID = "city_id";

    public static final String COL_AREA_ID = "area_id";

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

    public static OpeWhseBuilder builder() {
        return new OpeWhseBuilder();
    }
}