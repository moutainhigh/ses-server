package com.redescooter.ses.service.foundation.dm.base;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "com-redescooter-ses-service-foundation-dm-base-PlaAppVersion")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "pla_app_version")
public class PlaAppVersion implements Serializable {
    /**
     * ID
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "ID")
    private Long id;

    /**
     * 逻辑删除标识 0正常 1删除
     */
    @TableField(value = "dr")
    @TableLogic
    @ApiModelProperty(value = "逻辑删除标识 0正常 1删除")
    private Integer dr;

    /**
     * 系统ID
     */
    @TableField(value = "system_id")
    @ApiModelProperty(value = "系统ID")
    private String systemId;

    /**
     * 应用ID
     */
    @TableField(value = "app_id")
    @ApiModelProperty(value = "应用ID")
    private String appId;

    /**
     * IOS or ANDROID or SCS
     */
    @TableField(value = "system_type")
    @ApiModelProperty(value = "IOS or ANDROID or SCS")
    private Integer systemType;

    /**
     * 版本类型：1.IOS 2. ANDROID 3.SCS(车载平板)
     */
    @TableField(value = "type")
    @ApiModelProperty(value = "版本类型：1.IOS 2. ANDROID 3.SCS")
    private Integer type;

    /**
     * 版本应用编码
     */
    @TableField(value = "code")
    @ApiModelProperty(value = "版本应用编码")
    private String code;

    /**
     * 是否强制更新 1时true，0时false
     */
    @TableField(value = "is_force")
    @ApiModelProperty(value = "是否强制更新 1时true，0时false")
    private Integer isForce;

    /**
     * 更新提示内容
     */
    @TableField(value = "update_content")
    @ApiModelProperty(value = "更新提示内容")
    private String updateContent;

    /**
     * 更新地址
     */
    @TableField(value = "update_link")
    @ApiModelProperty(value = "更新地址")
    private String updateLink;

    /**
     * 新版本号
     */
    @TableField(value = "new_version_num")
    @ApiModelProperty(value = "新版本号")
    private String newVersionNum;

    /**
     * 安装包大小
     */
    @TableField(value = "package_size")
    @ApiModelProperty(value = "安装包大小")
    private String packageSize;

    /**
     * 新版本号名称
     */
    @TableField(value = "new_version_name")
    @ApiModelProperty(value = "新版本号名称")
    private String newVersionName;

    /**
     * 最小版本
     */
    @TableField(value = "min_version_num")
    @ApiModelProperty(value = "最小版本")
    private Integer minVersionNum;

    /**
     * 最小版本号名称
     */
    @TableField(value = "nin_version_name")
    @ApiModelProperty(value = "最小版本号名称")
    private String ninVersionName;

    /**
     * 状态 NEW:新版本；Closed：已关闭
     */
    @TableField(value = "status")
    @ApiModelProperty(value = "版本状态 0未发布 1已发布 2生效中")
    private Integer status;

    /**
     * 更新内容标题
     */
    @TableField(value = "title")
    @ApiModelProperty(value = "更新内容标题")
    private String title;

    /**
     * 发布环境 1-dev环境 2-test环境 3-pre环境 4-pro环境
     */
    @TableField(value = "release_environment")
    @ApiModelProperty(value = "发布环境 1-dev环境 2-test环境 3-pre环境 4-pro环境")
    private Integer releaseEnvironment;

    /**
     * 标签
     */
    @TableField(value = "label")
    @ApiModelProperty(value = "标签")
    private String label;

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
     * 更新人
     */
    @TableField(value = "updated_by")
    @ApiModelProperty(value = "更新人")
    private Long updatedBy;

    /**
     * 更新时间
     */
    @TableField(value = "updated_time")
    @ApiModelProperty(value = "更新时间")
    private Date updatedTime;

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

    public static final String COL_SYSTEM_ID = "system_id";

    public static final String COL_APP_ID = "app_id";

    public static final String COL_SYSTEM_TYPE = "system_type";

    public static final String COL_TYPE = "type";

    public static final String COL_CODE = "code";

    public static final String COL_IS_FORCE = "is_force";

    public static final String COL_UPDATE_CONTENT = "update_content";

    public static final String COL_UPDATE_LINK = "update_link";

    public static final String COL_NEW_VERSION_NUM = "new_version_num";

    public static final String COL_PACKAGE_SIZE = "package_size";

    public static final String COL_NEW_VERSION_NAME = "new_version_name";

    public static final String COL_MIN_VERSION_NUM = "min_version_num";

    public static final String COL_NIN_VERSION_NAME = "nin_version_name";

    public static final String COL_STATUS = "status";

    public static final String COL_TITLE = "title";

    public static final String COL_RELEASE_ENVIRONMENT = "release_environment";

    public static final String COL_LABEL = "label";

    public static final String COL_CREATED_BY = "created_by";

    public static final String COL_CREATED_TIME = "created_time";

    public static final String COL_UPDATED_BY = "updated_by";

    public static final String COL_UPDATED_TIME = "updated_time";

    public static final String COL_DEF1 = "def1";

    public static final String COL_DEF2 = "def2";

    public static final String COL_DEF3 = "def3";

    public static final String COL_DEF5 = "def5";

    public static final String COL_DEF6 = "def6";

    public static PlaAppVersionBuilder builder() {
        return new PlaAppVersionBuilder();
    }
}
