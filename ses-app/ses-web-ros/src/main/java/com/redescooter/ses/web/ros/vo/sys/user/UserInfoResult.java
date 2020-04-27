package com.redescooter.ses.web.ros.vo.sys.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;

/**
 * @ClassNameuserInfoEnter
 * @Description
 * @Author Joan
 * @Date2020/4/27 17:58
 * @Version V1.0
 **/

@ApiModel(value = "用户信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)

public class UserInfoResult extends GeneralResult {
    /**
     * ID
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "ID")
    private Long id;

    /**
     * 维修店ID
     */
    @TableField(value = "repair_shop_Id")
    @ApiModelProperty(value = "维修店ID")
    private Long repairShopId;

    /**
     * 用户ID
     */
    @TableField(value = "sys_user_id")
    @ApiModelProperty(value = "用户ID")
    private Long sysUserId;

    /**
     * 照片
     */
    @TableField(value = "picture")
    @ApiModelProperty(value = "照片")
    private String picture;

    /**
     * 名
     */
    @TableField(value = "first_name")
    @ApiModelProperty(value = "名")
    private String firstName;

    /**
     * 姓
     */
    @TableField(value = "last_name")
    @ApiModelProperty(value = "姓")
    private String lastName;

    /**
     * 全名
     */
    @TableField(value = "full_name")
    @ApiModelProperty(value = "全名")
    private String fullName;

    /**
     * 邮箱
     */
    @TableField(value = "email")
    @ApiModelProperty(value = "邮箱")
    private String email;

    /**
     * 国家编码如+86
     */
    @TableField(value = "country_code")
    @ApiModelProperty(value = "国家编码如+86")
    private String countryCode;

    /**
     * 电话号
     */
    @TableField(value = "tel_number")
    @ApiModelProperty(value = "电话号")
    private String telNumber;

    /**
     * 性别 Male 男 Female 女
     */
    @TableField(value = "gender")
    @ApiModelProperty(value = "性别 Male 男 Female 女")
    private String gender;

    /**
     * 生日
     */
    @TableField(value = "birthday")
    @ApiModelProperty(value = "生日")
    private Date birthday;

    /**
     * 出生地
     */
    @TableField(value = "place_birth")
    @ApiModelProperty(value = "出生地")
    private String placeBirth;

    /**
     * 地址国家编码
     */
    @TableField(value = "address_country_code")
    @ApiModelProperty(value = "地址国家编码")
    private String addressCountryCode;

    /**
     * 地址
     */
    @TableField(value = "address")
    @ApiModelProperty(value = "地址")
    private String address;

    /**
     * 办公地点Id(具体数据可查看 AddressBureauEnums )
     */
    @TableField(value = "address_bureau")
    @ApiModelProperty(value = "办公地点Id(具体数据可查看 AddressBureauEnums )")
    private String addressBureau;

    /**
     * 证件类型，1身份证，2驾驶证，3护照
     */
    @TableField(value = "certificate_type")
    @ApiModelProperty(value = "证件类型，1身份证，2驾驶证，3护照")
    private String certificateType;

    /**
     * 证件正面
     */
    @TableField(value = "certificate_negative_annex")
    @ApiModelProperty(value = "证件正面")
    private String certificateNegativeAnnex;

    /**
     * 证件背面
     */
    @TableField(value = "certificate_positive_annex")
    @ApiModelProperty(value = "证件背面")
    private String certificatePositiveAnnex;

}
