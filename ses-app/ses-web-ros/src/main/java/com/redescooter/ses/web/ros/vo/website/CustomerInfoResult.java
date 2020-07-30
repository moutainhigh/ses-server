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
@ApiModel(value = "", description = "")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class CustomerInfoResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "姓名")
    private String firstName;

    @ApiModelProperty(value = "姓名")
    private String lastName;

    @ApiModelProperty(value = "邮箱")
    private String email;

//    @ApiModelProperty(value = "电话")
//    private String telephone;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "维度")
    private String lat;

    @ApiModelProperty(value = "经度")
    private String lng;

    @ApiModelProperty(value = "地址Id")
    private String placeId;

    @ApiModelProperty(value = "区域Id")
    private String distrust;

    @ApiModelProperty(value = "国家")
    private String customerCountry;

    @ApiModelProperty(value = "城市")
    private String city;

    @ApiModelProperty(value = "国家Id")
    private Long countryId;
}
