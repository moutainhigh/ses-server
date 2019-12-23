package com.redescooter.ses.api.mobile.c.vo;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName:SaveUserPofileEnter
 * @description: SaveUserPofileEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/23 16:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class SaveUserPofileEnter extends GeneralEnter {


    /**
     * 逻辑删除标识 0正常 1删除
     */
    @ApiModelProperty(value="逻辑删除标识 0正常 1删除")
    private Integer dr;

    /**
     * 租户ID
     */
    @ApiModelProperty(value="租户ID")
    private Long tenantId;

    /**
     * 用户ID
     */
    @ApiModelProperty(value="用户ID")
    private Long userId;

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
     * 照片
     */
    @ApiModelProperty(value="照片")
    private String picture;

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
     * 性别
     */
    @ApiModelProperty(value="性别")
    private String gender;

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
