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
public class SaveUserProfileEnter extends GeneralEnter {

    @ApiModelProperty(value = "id 主键")
    private Long id;

    @ApiModelProperty(value="租户ID")
    private Long tenantId;

    @ApiModelProperty(value="用户ID")
    private Long userId;

    @ApiModelProperty(value="姓")
    private String firstName;

    @ApiModelProperty(value="名")
    private String lastName;

    @ApiModelProperty(value="姓名")
    private String fullName;

    @ApiModelProperty(value="照片")
    private String picture;

    @ApiModelProperty(value="邮箱")
    private String email1;

    @ApiModelProperty(value="电话号")
    private String telNumber1;

    @ApiModelProperty(value = "国家区号")
    private String countryCode1;

    @ApiModelProperty(value="性别")
    private String gender;

    @ApiModelProperty(value="证件类型1身份证，2驾驶证，3护照")
    private String certificateType;

    @ApiModelProperty(value="证件反面图片")
    private String certificateNegativeAnnex;

    @ApiModelProperty(value="证件正面图片")
    private String certificatePositiveAnnex;

    @ApiModelProperty(value="居住地址")
    private String address;
}
