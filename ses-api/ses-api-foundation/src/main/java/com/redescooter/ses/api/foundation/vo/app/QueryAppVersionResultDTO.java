package com.redescooter.ses.api.foundation.vo.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 查询应用版本列表返回结果 DTO
 * @author assert
 * @date 2020/12/9 17:35
 */
@Data
@ApiModel(value = "查询应用版本列表返回结果")
public class QueryAppVersionResultDTO implements Serializable {

    @ApiModelProperty(value = "主键Id", dataType = "Long")
    private Long id;

    @ApiModelProperty(value = "系统类型", dataType = "Integer")
    private Integer systemType;

    @ApiModelProperty(value = "版本类型 1-IOS 2-ANDROID 3-SCS(车载平板) 4-SaaS 5-Server(后台服务) 6-ROS",
            dataType = "Integer")
    private Integer type;

    @ApiModelProperty(value = "版本应用编码", dataType = "String")
    private String code;

    @ApiModelProperty(value = "是否强制更新 0否 1是", dataType = "Integer")
    private Integer isForce;

    @ApiModelProperty(value = "更新内容", dataType = "String")
    private String updateContent;

    @ApiModelProperty(value = "更新包地址", dataType = "String")
    private String updateLink;

    @ApiModelProperty(value = "新版本号", dataType = "String")
    private String newVersionNum;

    @ApiModelProperty(value = "安装包大小", dataType = "String")
    private String packageSize;

    @ApiModelProperty(value = "新版本号名称", dataType = "String")
    private String newVersionName;

    @ApiModelProperty(value = "最小版本", dataType = "Integer")
    private Integer minVersionNum;

    @ApiModelProperty(value = "最小版本号名称", dataType = "String")
    private String ninVersionName;

    @ApiModelProperty(value = "版本状态 0未发布 1已发布 2使用中", dataType = "Integer", example = "0")
    private Integer status;

    @ApiModelProperty(value = "更新内容标题", dataType = "String")
    private String title;

    @ApiModelProperty(value = "发布环境 1-dev环境 2-test环境 3-pre环境 4-pro环境", dataType = "Integer")
    private Integer releaseEnvironment;

    @ApiModelProperty(value = "标签", dataType = "String")
    private String label;

    @ApiModelProperty(value = "创建人id", dataType = "Long")
    private Long createdBy;

    @ApiModelProperty(value = "创建人姓名", dataType = "String")
    private String createdName;

    @ApiModelProperty(value = "创建人头像", dataType = "String")
    private String headPortrait;

    @ApiModelProperty(value = "创建时间", dataType = "Date")
    private Date createdTime;

    @ApiModelProperty(value = "更新人id", dataType = "Long")
    private Long updatedBy;

    @ApiModelProperty(value = "更新时间", dataType = "Date")
    private Date updatedTime;

}
