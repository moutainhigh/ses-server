package com.redescooter.ses.service.foundation.dm.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@ApiModel(value = "com-redescooter-ses-service-foundation-dm-base-PlaPushResult")
@Data
@TableName(value = "pla_push_result")
public class PlaPushResult implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 逻辑删除标识 0正常 1删除
     */
    @TableField(value = "dr")
    @TableLogic
    @ApiModelProperty(value = "逻辑删除标识 0正常 1删除")
    private Integer dr;

    /**
     * 推送返回消息主键 无返回值 默认为0
     */
    @TableField(value = "msg_id")
    @ApiModelProperty(value = "推送返回消息主键 无返回值 默认为0")
    private Long msgId;

    /**
     * 推送序号 无返回值 默认为0
     */
    @TableField(value = "send_no")
    @ApiModelProperty(value = "推送序号 无返回值 默认为0")
    private Integer sendNo;

    /**
     * 状态 无返回值 默认为5 区分极光成功时 返回值是0
     */
    @TableField(value = "status_code")
    @ApiModelProperty(value = "状态 无返回值 默认为5 区分极光成功时 返回值是0")
    private Integer statusCode;

    /**
     * 错误码 由于成功状态 无错误码 自定义为 1  表示成功
     */
    @TableField(value = "error_code")
    @ApiModelProperty(value = "错误码 由于成功状态 无错误码 自定义为 1  表示成功")
    private Integer errorCode;

    /**
     * 错误信息 成功无错误信息 自定义 成功是 错误信息为空
     */
    @TableField(value = "error_message")
    @ApiModelProperty(value = "错误信息 成功无错误信息 自定义 成功是 错误信息为空")
    private String errorMessage;

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

    public static final String COL_MSG_ID = "msg_id";

    public static final String COL_SEND_NO = "send_no";

    public static final String COL_STATUS_CODE = "status_code";

    public static final String COL_ERROR_CODE = "error_code";

    public static final String COL_ERROR_MESSAGE = "error_message";

    public static final String COL_CREATED_BY = "created_by";

    public static final String COL_CREATED_TIME = "created_time";

    public static final String COL_DEF1 = "def1";

    public static final String COL_DEF2 = "def2";

    public static final String COL_DEF3 = "def3";

    public static final String COL_DEF5 = "def5";

    public static final String COL_DEF6 = "def6";
}