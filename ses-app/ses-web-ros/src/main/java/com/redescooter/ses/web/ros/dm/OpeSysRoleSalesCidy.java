package com.redescooter.ses.web.ros.dm;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@ApiModel(value = "com-redescooter-ses-web-ros-dm-OpeSysRoleSalesCidy")
@Data
@Builder
@TableName(value = "ope_sys_role_sales_cidy")
public class OpeSysRoleSalesCidy {

    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "ID")
    private Long id;

    /**
     * 用户ID
     */
    @TableField(value = "role_id")
    @ApiModelProperty(value = "用户ID")
    private Long roleId;

    /**
     * 城市ID
     */
    @TableField(value = "city_id")
    @ApiModelProperty(value = "城市ID")
    private Long cityId;

    public static final String COL_ROLE_ID = "role_id";

    public static final String COL_CITY_ID = "city_id";

    public static final String COL_ID = "id";

    public OpeSysRoleSalesCidy() {

    }

    public OpeSysRoleSalesCidy(Long roleId, Long cityId) {
        this.roleId = roleId;
        this.cityId = cityId;
    }

}