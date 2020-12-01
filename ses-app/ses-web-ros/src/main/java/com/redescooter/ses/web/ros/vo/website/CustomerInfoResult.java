package com.redescooter.ses.web.ros.vo.website;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @ClassName:CustomerInfoResult
 * @description: CustomerInfoResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/05/15 15:49
 */
@ApiModel(value = "Customer Info", description = "Customer Info")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class CustomerInfoResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "first name")
    private String firstName;

    @ApiModelProperty(value = "last name")
    private String lastName;

    @ApiModelProperty(value = "email")
    private String email;

    @ApiModelProperty(value = "address")
    private String address;

    @ApiModelProperty(value = "latitude")
    private String lat;

    @ApiModelProperty(value = "longitude")
    private String lng;

    @ApiModelProperty(value = "adress Id")
    private String placeId;

    @ApiModelProperty(value = "district")
    private String district;

    @ApiModelProperty(value = "Country")
    private String customerCountry;

    @ApiModelProperty(value = "city")
    private String city;

    @ApiModelProperty(value = "Country Id")
    private Long countryId;
}
