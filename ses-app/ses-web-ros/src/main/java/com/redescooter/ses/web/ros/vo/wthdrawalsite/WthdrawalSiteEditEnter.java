package com.redescooter.ses.web.ros.vo.wthdrawalsite;


import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.exception.ValidationExceptionBaseCode;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@ApiModel(value = "Wthdrawal Site Edit Enter", description = "Wthdrawal Site Edit Enter")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
public class WthdrawalSiteEditEnter extends GeneralEnter {

    @ApiModelProperty(value = "ID")
    @NotNull(code = ValidationExceptionBaseCode.ID_IS_EMPTY, message = "主键不能为空")
    private Long id;

    @ApiModelProperty(value = "Store Name")
    @NotNull(code = ValidationExceptionBaseCode.NAME_ILLEAGE, message = "名称不能为空")
    private String storeName;

    @ApiModelProperty(value = "Shop type [1 maintenance, - 1 sales, 0 all]")
    private Integer type;

    @ApiModelProperty(value = "Business status [0 open, 1 close]")
    private Integer businessStatus;

    @ApiModelProperty(value = "Street Number")
    private String streetNumber;

    @ApiModelProperty(value = "Street Name")
    private String streetName;

    @ApiModelProperty(value = "Contact First")
    private String contactFirst;

    @ApiModelProperty(value = "Contact Last")
    private String contactLast;

    @ApiModelProperty(value = "Contant Full Name")
    private Integer contantFullName;

    @ApiModelProperty(value = "Store Email")
    private String email;

    @ApiModelProperty(value = "Phone Number")
    private String phoneNumber;

    @ApiModelProperty(value = "Open Time")
    private Date openTime;

    @ApiModelProperty(value = "Close Time")
    private Date closeTime;

    @ApiModelProperty(value = "country")
    private String country;

    @ApiModelProperty(value = "city")
    private String city;

    @ApiModelProperty(value = "area")
    private String area;

    @ApiModelProperty(value = "Code Postal")
    private String codePostal;

    @ApiModelProperty(value = "address")
    private String address;

    @ApiModelProperty(value = "remarks")
    private String remarks;

    @ApiModelProperty(value = "Custom fields")
    private String def1;

    @ApiModelProperty(value = "other_params,It is stored in JSON data format, key and value")
    private String otherParams;
}
