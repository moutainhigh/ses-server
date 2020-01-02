package com.redescooter.ses.web.delivery.vo;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 31/12/2019 1:53 下午
 * @ClassName: SaveDriverEnter
 * @Function: TODO
 */
@ApiModel(value = "创建司机", description = "创建司机")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class SaveDriverEnter extends GeneralEnter {

    @ApiModelProperty(value = "司机主键")
    private Long id;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "司机姓氏")
    private String driverFirstName;

    @ApiModelProperty(value = "司机名字")
    private String driverLastName;

    @ApiModelProperty(value = "司机性别")
    private String gender;

    @ApiModelProperty(value = "手机区号")
    private String countryCodel;

    @ApiModelProperty(value = "司机手机号")
    private String driverPhone;

    @ApiModelProperty(value = "司机邮箱")
    private String email;

    @ApiModelProperty(value = "司机地址")
    private String address;

    @ApiModelProperty(value = "司机生日")
    private String birthday;

    @ApiModelProperty(value = "证件类型1身份证，2驾驶证，3护照")
    private String certificateType;

    @ApiModelProperty(value = "驾驶证")
    private String driverLicense;

    @ApiModelProperty(value = "驾驶证附件上")
    private String driverLicenseUpAnnex;

    @ApiModelProperty(value = "驾驶证附件下")
    private String driverLicenseDownAnnex;

}
