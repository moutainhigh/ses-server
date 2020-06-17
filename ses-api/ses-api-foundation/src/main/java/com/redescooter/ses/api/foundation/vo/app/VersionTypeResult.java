package com.redescooter.ses.api.foundation.vo.app;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassNameAppTypeResult
 * @Description
 * @Author Joan
 * @Date2020/6/17 14:02
 * @Version V1.0
 **/
@Data
@ApiModel(value = "应用版本结果集", description = "应用版本结果集")
public class VersionTypeResult extends GeneralResult {
  /**
   * 系统ID
   */
  @ApiModelProperty(value = "系统ID")
  private String systemId;

  /**
   * 应用ID
   */
  @ApiModelProperty(value = "应用ID")
  private String appId;

  /**
   * 1.IOS 2. ANDROID
   */
  @ApiModelProperty(value = "1.IOS 2. ANDROID")
  private Integer systemType;

  /**
   * 1.rps  2.singlechip
   */
  @ApiModelProperty(value = "1.rps  2.singlechip")
  private Integer type;

  /**
   * 应用code
   */
  @ApiModelProperty(value = "应用code")
  private String code;

  /**
   * 是否强制更新 1时true，0时false
   */
  @ApiModelProperty(value = "是否强制更新 1时true，0时false")
  private Integer isForce;

  /**
   * 更新提示内容
   */
  @ApiModelProperty(value = "更新提示内容")
  private String updateContent;

  /**
   * 更新地址
   */
  @ApiModelProperty(value = "更新地址")
  private String updateLink;

  /**
   * 新版本号
   */
  @ApiModelProperty(value = "新版本号")
  private Integer newVersionNum;

  /**
   * 安装包大小
   */
  @ApiModelProperty(value = "安装包大小")
  private String packageSize;

  /**
   * 新版本号名称
   */
  @ApiModelProperty(value = "新版本号名称")
  private String newVersionName;

  /**
   * 最小版本
   */
  @ApiModelProperty(value = "最小版本")
  private Integer minVersionNum;

  /**
   * 最小版本号名称
   */
  @ApiModelProperty(value = "最小版本号名称")
  private String ninVersionName;

  /**
   * 状态 NEW:新版本；Closed：已关闭
   */
  @ApiModelProperty(value = "状态 NEW:新版本；Closed：已关闭")
  private String status;
}
