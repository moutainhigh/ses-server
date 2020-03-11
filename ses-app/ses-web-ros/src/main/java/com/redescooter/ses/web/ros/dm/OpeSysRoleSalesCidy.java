package com.redescooter.ses.web.ros.dm;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@ApiModel(value="com-redescooter-ses-web-ros-dm-OpeSysRoleSalesCidy")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "ope_sys_role_sales_cidy")
public class OpeSysRoleSalesCidy implements Serializable {
    /**
     * 用户ID
     */
    @TableField(value = "role_id")
    @ApiModelProperty(value="用户ID")
    private Long roleId;

    /**
     * 城市ID
     */
    @TableField(value = "city_id")
    @ApiModelProperty(value="城市ID")
    private Long cityId;

    private static final long serialVersionUID = 1L;

    public static final String COL_ROLE_ID = "role_id";

    public static final String COL_CITY_ID = "city_id";
}