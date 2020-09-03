package com.redescooter.ses.web.ros.vo.salearea;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @ClassNameSaleAreaSaveEnter
 * @Description
 * @Author Aleks
 * @Date2020/9/3 14:38
 * @Version V1.0
 **/
@Data
public class SaleAreaSaveEnter extends GeneralEnter {

    @ApiModelProperty(value="主键id")
    private Long id;

    /**
     * 父级区域id
     */
    @TableField(value = "parent_id")
    @ApiModelProperty(value="父级区域id")
    private Long parentId;

    /**
     * 区域编码
     */
    @TableField(value = "area_code")
    @ApiModelProperty(value="区域编码")
    private String areaCode;

    /**
     * 区域名称
     */
    @TableField(value = "area_name")
    @ApiModelProperty(value="区域名称")
    private String areaName;

    /**
     * 区域等级
     */
    @TableField(value = "level")
    @ApiModelProperty(value="区域等级")
    private Integer level;

    /**
     * 经度
     */
    @TableField(value = "longitude")
    @ApiModelProperty(value="经度")
    private String longitude;

    /**
     * 纬度
     */
    @TableField(value = "latitude")
    @ApiModelProperty(value="纬度")
    private String latitude;

}
