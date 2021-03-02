package com.redescooter.ses.api.foundation.vo.app;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 应用版本管理 DTO
 * @author assert
 * @date 2020/11/30 12:03
 */
@Data
@ApiModel(value = "应用版本信息DTO")
public class AppVersionDTO extends GeneralEnter {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键Id", dataType = "Long")
    private Long id;

    /**
     * 版本类型：1-IOS 2-ANDROID 3-SCS(车载平板) 4-SaaS 5-Server(后台服务) 6-ROS
     */
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
    @ApiModelProperty(value = "更新内容", dataType = "String", required = true)
    private String updateContent;

    /**
     * 更新包地址
     */
    @ApiModelProperty(value = "更新包地址", dataType = "String", required = true)
    private String updateLink;

    /**
     * 新版本号
     */
    @ApiModelProperty(value = "新版本号", dataType = "String", required = true)
    private String newVersionNum;

    /**
     * 版本编码
     */
    @ApiModelProperty(value = "版本编码", dataType = "String", required = true)
    private String code;

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
    @ApiModelProperty(value = "发布环境 1-dev环境 2-test环境 3-pre环境 4-pro环境", dataType = "Integer", required = true)
    private Integer releaseEnvironment;

    /**
     * 标签
     */
    @ApiModelProperty(value = "标签", dataType = "String", required = true)
    private String label;

}
