package com.redescooter.ses.web.delivery.vo;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 2/1/2020 10:50 上午
 * @ClassName: ListDriverPage
 * @Function: TODO
 */

@ApiModel(value = "司机列表结果集", description = "司机列表结果集")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class ListDriverResult extends GeneralResult {

    @ApiModelProperty(value = "司机主键")
    private Long id;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "司机状态")
    private String status;

    @ApiModelProperty(value = "司机姓氏")
    private String driverFirstName;

    @ApiModelProperty(value = "司机名字")
    private String driverLastName;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "司机地址")
    private String address;

    @ApiModelProperty(value = "车牌号")
    private String plateNumber;

    @ApiModelProperty(value = "手机区号")
    private String countryCodel;

    @ApiModelProperty(value = "司机手机号")
    private String driverPhone;

    @ApiModelProperty(value = "账号是否激活")
    private Boolean activatBoolean;
}
