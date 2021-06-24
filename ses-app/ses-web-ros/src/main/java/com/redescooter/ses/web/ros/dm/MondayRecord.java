package com.redescooter.ses.web.ros.dm;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.redescooter.ses.api.common.annotation.NotNull;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: //
 * @Date: 2021-06-23 12:50:18
 * @Author: Charles
 */
@Data
@TableName("monday_record")
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "monday记录表")
public class MondayRecord extends Model<MondayRecord> implements Serializable {

    private static final long serialVersionUID = -88032707439217778L;

    @ApiModelProperty("id")
    private Long id;

    /**
     * 逻辑删除
     */
    @TableField(value = "dr")
    @ApiModelProperty("逻辑删除")
    private Integer dr;

    /**
     * monday工作空间
     */
    @ApiModelProperty("monday工作空间")
    private String workspaceId;

    /**
     * monday板id
     */
    @NotNull
    @ApiModelProperty("monday板id")
    private String boardId;

    /**
     * monday分组名字
     */
    @ApiModelProperty("monday分组名字")
    private String groupName;

    /**
     * monday列名
     */
    @ApiModelProperty("monday列名")
    private String columnNames;

    /**
     * monday创建行id
     */
    @ApiModelProperty("monday创建行id")
    private String itemId;

    /**
     * monday行数据存储json
     */
    @ApiModelProperty("monday行数据存储json")
    private String itemValue;

    /**
     * rsn码
     */
    @ApiModelProperty("rsn码")
    private String rsn;

    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
    private Long createdBy;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date createdTime;

    /**
     * 修改人
     */
    @ApiModelProperty("修改人")
    private Long updatedBy;

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
