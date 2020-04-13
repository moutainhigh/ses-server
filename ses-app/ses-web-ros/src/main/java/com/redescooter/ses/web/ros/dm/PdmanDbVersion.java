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

@ApiModel(value = "com-redescooter-ses-web-ros-dm-PdmanDbVersion")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "pdman_db_version")
public class PdmanDbVersion implements Serializable {
    @TableField(value = "DB_VERSION")
    @ApiModelProperty(value = "")
    private String dbVersion;

    @TableField(value = "VERSION_DESC")
    @ApiModelProperty(value = "")
    private String versionDesc;

    @TableField(value = "CREATED_TIME")
    @ApiModelProperty(value = "")
    private String createdTime;

    private static final long serialVersionUID = 1L;

    public static final String COL_DB_VERSION = "DB_VERSION";

    public static final String COL_VERSION_DESC = "VERSION_DESC";

    public static final String COL_CREATED_TIME = "CREATED_TIME";
}