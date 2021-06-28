package com.redescooter.ses.web.ros.dm;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.*;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

/**
 * @Description: // monday配置相关信息(MondayConfig)表实体类
 * @Date: 2021-06-28 17:36:44
 * @Author: Charles
 */
@Data
@TableName("monday_config")
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "monday配置相关信息")
public class MondayConfig extends Model<MondayConfig> implements Serializable {

    private static final long serialVersionUID = 427933480691620700L;


    @ApiModelProperty("主键id")
    private Long id;

    /**
     * 逻辑删除：0未删除，1已删除
     */
    @TableField(value = "dr")
    @ApiModelProperty("逻辑删除：0未删除，1已删除")
    private Integer dr;

    /**
     * 板子的名字
     */
    @ApiModelProperty("板子的名字")
    private String boardName;

    /**
     * 板子对应的id
     */
    @ApiModelProperty("板子对应的id")
    private String boardId;

    /**
     * 分组名字
     */
    @ApiModelProperty("分组名字")
    private String groupName;

    /**
     * 空间id
     */
    @ApiModelProperty("空间id")
    private String workspaceId;

    /**
     * api 密钥
     */
    @ApiModelProperty("api 密钥")
    private String authorization;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date createdTime;

    /**
     * 修改时间
     */
    @ApiModelProperty("修改时间")
    private Date updatedTime;

    /**
     * 冗余字段
     */
    @ApiModelProperty("冗余字段")
    private String def1;

    /**
     * 冗余字段
     */
    @ApiModelProperty("冗余字段")
    private String def2;

    /**
     * 冗余字段
     */
    @ApiModelProperty("冗余字段")
    private String def3;

    /**
     * 冗余字段
     */
    @ApiModelProperty("冗余字段")
    private String def5;

    /**
     * 冗余字段
     */
    @ApiModelProperty("冗余字段")
    private String def6;

}
