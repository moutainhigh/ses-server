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

@ApiModel(value="com-redescooter-ses-web-ros-dm-OpeSysDeptRelation")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "ope_sys_dept_relation")
public class OpeSysDeptRelation implements Serializable {
    /**
     * 祖先节点
     */
    @TableField(value = "ancestor")
    @ApiModelProperty(value="祖先节点")
    private Long ancestor;

    /**
     * 后代节点
     */
    @TableField(value = "descendant")
    @ApiModelProperty(value="后代节点")
    private Long descendant;

    private static final long serialVersionUID = 1L;

    public static final String COL_ANCESTOR = "ancestor";

    public static final String COL_DESCENDANT = "descendant";
}