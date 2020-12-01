package com.redescooter.ses.web.ros.vo.specificat;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @ClassNameSpecificatGroupListResult
 * @Description
 * @Author Aleks
 * @Date2020/9/28 16:17
 * @Version V1.0
 **/
@Data
public class SpecificatGroupListResult {

    @ApiModelProperty(value="主键")
    private Long id;


    @TableField(value = "group_name")
    @ApiModelProperty(value="分组名称")
    private String groupName;

    /**
     * 产品类型，1：整车，2：组装
     */
    @TableField(value = "production_type")
    @ApiModelProperty(value="产品类型，1：整车，2：组装")
    private Integer productionType;


    @ApiModelProperty(value = "创建人名称")
    private String createdName;

    /**
     * 创建时间
     */
    @TableField(value = "created_time")
    @ApiModelProperty(value="创建时间")
    private Date createdTime;

    @ApiModelProperty(value = "更新人")
    private String updatedName;

    @ApiModelProperty(value = "修改时间")
    private Date updatedTime;

}
