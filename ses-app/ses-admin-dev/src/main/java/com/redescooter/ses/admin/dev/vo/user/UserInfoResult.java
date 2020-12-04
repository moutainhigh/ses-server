package com.redescooter.ses.admin.dev.vo.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @ClassNameUserInfoResult
 * @Description
 * @Author Aleks
 * @Date2020/12/4 11:46
 * @Version V1.0
 **/
@Data
@ApiModel(value = "登陆的用户信息")
public class UserInfoResult extends GeneralResult {

    @ApiModelProperty(value = "照片")
    private String picture;

    @TableField(value = "first_name")
    @ApiModelProperty(value = "名")
    private String firstName = "rede";

    /**
     * 姓
     */
    @TableField(value = "last_name")
    @ApiModelProperty(value = "姓")
    private String lastName = "rede";

    /**
     * 全名
     */
    @ApiModelProperty(value = "全名")
    private String fullName = "rede rede";

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    private String email;

    /**
     * 国家编码如+86
     */
    @ApiModelProperty(value = "国家编码如+86")
    private String countryCode;

    /**
     * 电话号
     */
    @ApiModelProperty(value = "电话号")
    private String telNumber;

    /**
     * 性别 Male 男 Female 女
     */
    @ApiModelProperty(value = "性别 Male 男 Female 女")
    private String gender;

    /**
     * 生日
     */
    @ApiModelProperty(value = "生日")
    private Date birthday;

    /**
     * 出生地
     */
    @ApiModelProperty(value = "出生地")
    private String placeBirth;

    /**
     * 地址国家编码
     */
    @ApiModelProperty(value = "地址国家编码")
    private String addressCountryCode;

    /**
     * 地址
     */
    @ApiModelProperty(value = "地址")
    private String address;

    /**
     * 办公地点Id(具体数据可查看 AddressBureauEnums )
     */
    @ApiModelProperty(value = "办公地点Id(具体数据可查看 AddressBureauEnums )")
    private String addressBureau;

    /**
     * 证件类型，1身份证，2驾驶证，3护照
     */
    @ApiModelProperty(value = "证件类型，1身份证，2驾驶证，3护照")
    private String certificateType;

    /**
     * 证件正面
     */
    @ApiModelProperty(value = "证件正面")
    private String certificateNegativeAnnex;

    /**
     * 证件背面
     */
    @ApiModelProperty(value = "证件背面")
    private String certificatePositiveAnnex;

    @ApiModelProperty("Do you need to reset your password")
    private boolean resetPsd;

}
