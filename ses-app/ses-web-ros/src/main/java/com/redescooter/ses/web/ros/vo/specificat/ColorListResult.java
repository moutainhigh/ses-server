package com.redescooter.ses.web.ros.vo.specificat;

import com.baomidou.mybatisplus.annotation.TableField;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @ClassNameColorListResult
 * @Description
 * @Author Aleks
 * @Date2020/9/28 15:12
 * @Version V1.0
 **/
@Data
public class ColorListResult extends GeneralResult {

    @ApiModelProperty(value="主键")
    private Long id;

    @ApiModelProperty(value="颜色名称")
    private String colorName;

    @ApiModelProperty(value="色值")
    private String colorValue;

    @ApiModelProperty(value="创建人id")
    private Long createdBy;

    @ApiModelProperty(value = "创建人名称")
    private String createdName;

    /**
     * 创建时间
     */
    @TableField(value = "created_time")
    @ApiModelProperty(value="创建时间")
    private Date createdTime;

}
