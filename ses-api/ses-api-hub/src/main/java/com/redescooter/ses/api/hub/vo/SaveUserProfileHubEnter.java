package com.redescooter.ses.api.hub.vo;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName:UserProfileHubEnter
 * @description: UserProfileHubEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/23 15:28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class SaveUserProfileHubEnter extends GeneralEnter {

    /**
     * 租户ID
     */
    @ApiModelProperty(value="租户ID")
    private Long inputTenantId;

    /**
     * 用户ID
     */
    @ApiModelProperty(value="用户ID")
    private Long inputUserId;

    /**
     * 姓
     */
    @ApiModelProperty(value="姓")
    private String firstName;

    /**
     * 名
     */
    @ApiModelProperty(value="名")
    private String lastName;

    /**
     * 姓名
     */
    @ApiModelProperty(value="姓名")
    private String fullName;

    /**
     * 邮箱
     */
    @ApiModelProperty(value="邮箱")
    private String email1;


    /**
     * 电话号
     */
    @ApiModelProperty(value="电话号")
    private String telNumber1;

    /**
     * 证件类型1身份证，2驾驶证，3护照
     */
    @ApiModelProperty(value="证件类型1身份证，2驾驶证，3护照")
    private String certificateType;

    /**
     * 证件反面图片
     */
    @ApiModelProperty(value="证件反面图片")
    private String certificateNegativeAnnex;

    /**
     * 证件正面图片
     */
    @ApiModelProperty(value="证件正面图片")
    private String certificatePositiveAnnex;

    /**
     * 居住地址
     */
    @ApiModelProperty(value="居住地址")
    private String address;
}
