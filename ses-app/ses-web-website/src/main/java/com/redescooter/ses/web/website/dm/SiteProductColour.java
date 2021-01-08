package com.redescooter.ses.web.website.dm;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

/**
 * 产品颜色关系表
 */
@ApiModel(value = "com-redescooter-ses-web-website-dm-SiteProductColour")
@Data
@TableName(value = "site_product_colour")
public class SiteProductColour implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 主键
     */
    @TableField(value = "product_id")
    @ApiModelProperty(value = "主键")
    private Long productId;

    /**
     * 主键
     */
    @TableField(value = "colour_id")
    @ApiModelProperty(value = "主键")
    private Long colourId;

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

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_PRODUCT_ID = "product_id";

    public static final String COL_COLOUR_ID = "colour_id";

    public static final String COL_SYNCHRONIZE_FLAG = "synchronize_flag";

    public static final String COL_REVISION = "revision";
}