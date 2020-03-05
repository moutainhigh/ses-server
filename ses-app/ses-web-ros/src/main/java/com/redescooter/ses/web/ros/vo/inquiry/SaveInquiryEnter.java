package com.redescooter.ses.web.ros.vo.inquiry;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.web.ros.exception.ValidationExceptionCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName:saveInquiryEnter
 * @description: saveInquiryEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/05 14:48
 */
@ApiModel(value = "编辑询价单", description = "编辑询价单")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class SaveInquiryEnter extends GeneralResult {
    @ApiModelProperty(value = "城市")
    private Long city;

    @ApiModelProperty(value = "区域")
    private Long distrust;

    @ApiModelProperty(value = "邮箱")
    @NotNull(code = ValidationExceptionCode.EMAIL_IS_EMPTY, message = "邮箱不能为空")
    private String email;

    @ApiModelProperty(value = "客户名字")
    private String customerFirstName;

    @ApiModelProperty(value = "客户姓氏")
    private String customerLastName;

    @ApiModelProperty(value = "客户全名")
    private String customerFullName;

    @ApiModelProperty(value = "企业名称")
    private String companyName;

    @ApiModelProperty(value = "客户类型,ENTERPRISE-公司-1, PERSONAL-个人-2")
    private String customerType;

    @ApiModelProperty(value = "客户行业类型,RESTAURANT-餐厅-1, EXPRESS_DELIVERY-快递-2")
    private String industryType;

    @ApiModelProperty(value = "联系人名字")
    private String contactFirstName;

    @ApiModelProperty(value = "联系人姓氏")
    private String contactLastName;

    @ApiModelProperty(value = "国家编码，如手机号 中国 +86")
    private String countryCode;

    @ApiModelProperty(value = "电话")
    private String telephone;

    @ApiModelProperty(value = "车辆数量")
    private Integer scooterQuantity;

    @ApiModelProperty(value = "备注")
    private String remark;
}
