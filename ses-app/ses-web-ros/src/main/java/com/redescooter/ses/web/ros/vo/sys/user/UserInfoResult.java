package com.redescooter.ses.web.ros.vo.sys.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
    @ApiModelProperty(value = "维修店ID")
    private Long repairShopId;

    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID")
    private Long sysUserId;

    /**
     * 照片
     */
    @ApiModelProperty(value = "照片")
    private String picture;

    /**
     * 名
     */
    @ApiModelProperty(value = "名")
    private String firstName;

    /**
     * 姓
     */
    @ApiModelProperty(value = "姓")
    private String lastName;

    /**
     * 全名
     */
    @ApiModelProperty(value = "全名")
    private String fullName;

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

    @ApiModelProperty("是否可以重置密码标识")
    private boolean resetPsd;

}
