package com.redescooter.ses.web.ros.dm;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value="com-redescooter-ses-web-ros-dm-OpeExcleImport")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "ope_excle_import")
public class OpeExcleImport {
    /**
     * ID
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value="ID")
    private Long id;

    /**
     * 逻辑删除标识 0正常 1删除
     */
    @TableField(value = "dr")
    @ApiModelProperty(value="逻辑删除标识 0正常 1删除")
    @TableLogic
    private Integer dr;

    /**
     * 状态
     */
    @TableField(value = "status")
    @ApiModelProperty(value="状态")
    private String status;

    /**
     * 生产批次号
     */
    @TableField(value = "batch_no")
    @ApiModelProperty(value="生产批次号")
    private String batchNo;

    /**
     * 数据json保存记录
     */
    @TableField(value = "data_json")
    @ApiModelProperty(value="数据json保存记录")
    private String dataJson;

    /**
     * 导入成功数量
     */
    @TableField(value = "count")
    @ApiModelProperty(value="导入成功数量")
    private Integer count;

    /**
     * 附件
     */
    @TableField(value = "attachment")
    @ApiModelProperty(value="附件")
    private String attachment;

    /**
     * 业务类型
     */
    @TableField(value = "service_type")
    @ApiModelProperty(value="业务类型")
    private String serviceType;

    /**
     * 导入说明
     */
    @TableField(value = "message")
    @ApiModelProperty(value="导入说明")
    private String message;

    /**
     * 创建人
     */
    @TableField(value = "created_by")
    @ApiModelProperty(value="创建人")
    private Long createdBy;

    /**
     * 创建时间
     */
    @TableField(value = "created_time")
    @ApiModelProperty(value="创建时间")
    private Date createdTime;

    /**
     * 更新人
     */
    @TableField(value = "updated_by")
    @ApiModelProperty(value="更新人")
    private Long updatedBy;

    /**
     * 更新时间
     */
    @TableField(value = "updated_time")
    @ApiModelProperty(value="更新时间")
    private Date updatedTime;

    /**
     * 冗余字段
     */
    @TableField(value = "def1")
    @ApiModelProperty(value="冗余字段")
    private String def1;

    /**
     * 冗余字段
     */
    @TableField(value = "def2")
    @ApiModelProperty(value="冗余字段")
    private String def2;

    /**
     * 冗余字段
     */
    @TableField(value = "def3")
    @ApiModelProperty(value="冗余字段")
    private String def3;

    /**
     * 冗余字段
     */
    @TableField(value = "def5")
    @ApiModelProperty(value="冗余字段")
    private String def5;

    /**
     * 冗余字段
     */
    @TableField(value = "def6")
    @ApiModelProperty(value="冗余字段")
    private Double def6;

    public static final String COL_ID = "id";

    public static final String COL_DR = "dr";

    public static final String COL_STATUS = "status";

    public static final String COL_BATCH_NO = "batch_no";

    public static final String COL_DATA_JSON = "data_json";

    public static final String COL_COUNT = "count";

    public static final String COL_ATTACHMENT = "attachment";

    public static final String COL_SERVICE_TYPE = "service_type";

    public static final String COL_MESSAGE = "message";

    public static final String COL_CREATED_BY = "created_by";

    public static final String COL_CREATED_TIME = "created_time";

    public static final String COL_UPDATED_BY = "updated_by";

    public static final String COL_UPDATED_TIME = "updated_time";

    public static final String COL_DEF1 = "def1";

    public static final String COL_DEF2 = "def2";

    public static final String COL_DEF3 = "def3";

    public static final String COL_DEF5 = "def5";

    public static final String COL_DEF6 = "def6";
}