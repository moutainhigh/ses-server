package com.redescooter.ses.web.ros.vo;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName:CustomerDetailResult
 * @description: CustomerDetailResult
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/18 16:37
 */
@ApiModel(value = "客户详情入参", description = "客户详情入参")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class CustomerDetailResult extends GeneralResult {

    @ApiModelProperty(value = "Id")
    private Long id;

    @ApiModelProperty(value = "客户类型",allowableValues = "ENTERPRISE,PERSONAL")
    private String userType;

    @ApiModelProperty(value = "客户行业",allowableValues = "RESTAURANT,EXPRESS_DELIVERY")
    private String industry;

    @ApiModelProperty(value = "客户姓名")
    private String fistName;

    @ApiModelProperty(value = "客户姓名")
    private String lastName;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "邮件")
    private String email;

    @ApiModelProperty(value = "cityId")
    private Long cityId;

    @ApiModelProperty(value = "areaId")
    private Long areaId;

    @ApiModelProperty(value = "地址，拼接好的 完整地址")
    private String address;

    @ApiModelProperty(value = "longitude")
    private String longitude;

    @ApiModelProperty(value = "latitude")
    private String latitude;

    @ApiModelProperty(value = "placeId")
    private String placeId;

    @ApiModelProperty(value = "车辆需求数")
    private Integer scooterQty;

    @ApiModelProperty(value = "证件类型")
    private String certificateType;

    @ApiModelProperty(value = "证件正面")
    private String certificatePositivePicture;

    @ApiModelProperty(value = "证件反面")
    private String certificateNegativePicture;

    @ApiModelProperty(value = "合同图片")
    private String contractPicture;

    @ApiModelProperty(value = "营业执照附件")
    private String BusinessLicensePicture;

    @ApiModelProperty(value = "发票号码")
    private String invoiceNumber;

    @ApiModelProperty(value = "发票图片")
    private String invoicePicture;
}
