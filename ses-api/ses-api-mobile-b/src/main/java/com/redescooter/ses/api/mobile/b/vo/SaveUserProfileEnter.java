package com.redescooter.ses.api.mobile.b.vo;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @ClassName:SaveUserProfileEnter
 * @description: SaveUserProfileEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/30 15:31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
public class SaveUserProfileEnter extends GeneralEnter {
    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "租户ID", hidden = true)
    private Long tenantId;

    @ApiModelProperty(value = "用户ID", hidden = true)
    private Long userId;

    @ApiModelProperty(value = "照片")
    private String picture;

    @ApiModelProperty(value = "名")
    private String firstName;

    @ApiModelProperty(value = "姓")
    private String lastName;

    @ApiModelProperty(value = "全名")
    private String fullName;

    @ApiModelProperty(value = "邮箱", hidden = true)
    private String email1;

    @ApiModelProperty(value = "电话号")
    private String telNumber1;

    @ApiModelProperty(value = "国家编码如+86")
    private String countryCode1;

    @ApiModelProperty(value = "性别", hidden = true)
    private String gender;

    @ApiModelProperty(value = "生日")
    private Date birthday;

    @ApiModelProperty(value = "证件类型1身份证，2驾驶证，3护照")
    private String certificateType;

    @ApiModelProperty(value = "证件反面图片")
    private String certificateNegativeAnnex;

    @ApiModelProperty(value = "证件正面图片")
    private String certificatePositiveAnnex;

    @ApiModelProperty(value = "出生地")
    private String placeBirth;

    @ApiModelProperty(value = "居住地")
    private String address;
}
