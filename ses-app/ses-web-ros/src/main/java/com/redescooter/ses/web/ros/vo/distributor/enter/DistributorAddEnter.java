package com.redescooter.ses.web.ros.vo.distributor.enter;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.exception.ValidationExceptionBaseCode;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.ros.exception.ValidationExceptionCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description 经销商新增入参
 * @Author Chris
 * @Date 2020/12/16 16:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "经销商新增入参", description = "经销商新增入参")
public class DistributorAddEnter extends GeneralEnter implements Serializable {

    private static final long serialVersionUID = 5581625095327696612L;

    @ApiModelProperty(value = "门店名称", required = true)
    @NotNull(code = ValidationExceptionCode.STORE_NAME_IS_NOT_EMPTY, message = "门店名称为空")
    private String name;

    @ApiModelProperty(value = "门店logo")
    private String logoUrl;

    @ApiModelProperty(value = "国家代码 86中国 33法国")
    private String countryCode;

    @ApiModelProperty(value = "电话", required = true)
    @NotNull(code = ValidationExceptionCode.STORE_TEL_IS_NOT_EMPTY, message = "门店电话为空")
    private String tel;

    @ApiModelProperty(value = "邮件地址", required = true)
    @NotNull(code = ValidationExceptionBaseCode.EMAIL_IS_EMPTY, message = "邮件地址为空")
    private String email;

    @ApiModelProperty(value = "地址", required = true)
    @NotNull(code = ValidationExceptionCode.STORE_ADDRESS_IS_NOT_EMPTY, message = "地址为空")
    private String address;

    @ApiModelProperty(value = "经度")
    private String longitude;

    @ApiModelProperty(value = "纬度")
    private String latitude;

    @ApiModelProperty(value = "邮编", required = true)
    @NotNull(code = ValidationExceptionCode.POSTAL_CODE_IS_EMPTY, message = "邮编为空")
    private String cp;

    @ApiModelProperty(value = "城市", required = true)
    @NotNull(code = ValidationExceptionCode.STORE_CITY_IS_NOT_EMPTY, message = "城市为空")
    private String city;

    @ApiModelProperty(value = "地区", required = true)
    @NotNull(code = ValidationExceptionCode.STORE_AREA_IS_NOT_EMPTY, message = "地区为空")
    private String area;

    @ApiModelProperty(value = "合同url", required = true)
    @NotNull(code = ValidationExceptionCode.STORE_CONTRACT_IS_NOT_EMPTY, message = "合同为空")
    private String contractUrl;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "门店类型 1销售 2维修 若多选通过逗号分隔", required = true)
    @NotNull(code = ValidationExceptionCode.STORE_TYPE_IS_NOT_EMPTY, message = "门店类型为空")
    private String type;

    @ApiModelProperty(value = "门店类型是销售可售卖的产品 传递id 若多选通过逗号分隔")
    private String saleProduct;

}
