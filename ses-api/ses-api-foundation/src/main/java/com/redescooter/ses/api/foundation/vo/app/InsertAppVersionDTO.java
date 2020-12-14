package com.redescooter.ses.api.foundation.vo.app;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.foundation.exception.ValidationExceptionCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 新增/修改应用版本入参 DTO
 * @author assert
 * @date 2020/12/14 15:29
 */
@Data
@ApiModel(value = "新增/修改应用版本入参")
public class InsertAppVersionDTO extends GeneralEnter {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键Id(新增时无需)", dataType = "Long")
    private Long id;

    /**
     * 版本类型：1-IOS 2-ANDROID 3-SCS(车载平板) 4-SaaS 5-Server(后台服务) 6-ROS
     */
    @NotNull(code = ValidationExceptionCode.VERSION_TYPE_IS_NOT_EMPTY, message = "版本类型不能为空")
    @ApiModelProperty(value = "版本类型 1-IOS 2-ANDROID 3-SCS(车载平板) 4-SaaS 5-Server(后台服务) 6-ROS",
            dataType = "Integer", required = true)
    private Integer type;

    /**
     * 是否强制更新 1时true，0时false
     */
    @ApiModelProperty(value = "是否强制更新 0否 1是", dataType = "Integer", hidden = true)
    private Integer isForce;

    /**
     * 更新提示内容
     */
    @NotNull(code = ValidationExceptionCode.VERSION_UPDATE_CONTENT_IS_NOT_EMPTY, message = "更新内容不能为空")
    @ApiModelProperty(value = "更新内容", dataType = "String", required = true)
    private String updateContent;

    /**
     * 更新包地址
     */
    @NotNull(code = ValidationExceptionCode.VERSION_UPDATE_LINK_IS_NOT_EMPTY, message = "更新包地址不能为空")
    @ApiModelProperty(value = "更新包地址", dataType = "String", required = true)
    private String updateLink;

    /**
     * 新版本号
     */
    @NotNull(code = ValidationExceptionCode.VERSION_NUMBER_IS_NOT_EMPTY, message = "版本号不能为空")
    @ApiModelProperty(value = "新版本号", dataType = "String", required = true)
    private String newVersionNum;

    /**
     * 版本编码
     */
    @ApiModelProperty(value = "版本编码", dataType = "String", required = true)
    private String versionCode;

    /**
     * 安装包大小
     */
    @ApiModelProperty(value = "安装包大小", dataType = "String")
    private String packageSize;

    /**
     * 新版本号名称
     */
    @ApiModelProperty(value = "新版本号名称", dataType = "String", required = true)
    private String newVersionName;

    /**
     * 更新内容标题
     */
    @ApiModelProperty(value = "更新内容标题", dataType = "String", required = true)
    private String title;

    /**
     * 发布环境 1-dev环境 2-test环境 3-pre环境 4-pro环境
     */
    @NotNull(code = ValidationExceptionCode.VERSION_RELEASE_ENVIRONMENT_IS_NOT_EMPTY, message = "发布环境不能为空")
    @ApiModelProperty(value = "发布环境 1-dev环境 2-test环境 3-pre环境 4-pro环境", dataType = "Integer", required = true)
    private Integer releaseEnvironment;

    /**
     * 标签
     */
    @ApiModelProperty(value = "标签", dataType = "String", required = true)
    private String label;

}
