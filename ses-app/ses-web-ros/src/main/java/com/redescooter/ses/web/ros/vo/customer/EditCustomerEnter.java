package com.redescooter.ses.web.ros.vo.customer;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.math.BigDecimal;

/**
 * @ClassName:SaveCustomerEnter
 * @description: CreateCustomerEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/18 15:31
 */
@ApiModel(value = "编辑客户", description = "编辑客户")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class EditCustomerEnter extends GeneralEnter {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "时区")
    private String timeZone;

    @ApiModelProperty(value = "城市")
    private Long city;

    @ApiModelProperty(value = "区域")
    private Long distrust;

    @ApiModelProperty(value = "销售")
    private Long salesId;

    @ApiModelProperty(value = "客户名字")
    private String firstName;

    @ApiModelProperty(value = "客户姓氏")
    private String lastName;

    @ApiModelProperty(value = "企业名称")
    private String companyName;

    @ApiModelProperty(value = "客户头像")
    private String picture;

    @ApiModelProperty(value = "客户来源渠道 官网/email/电话")
    private String customerSource;

    @ApiModelProperty(value = "客户类型 1企业/2个人")
    private Integer customerType;

    @ApiModelProperty(value = "客户行业类型，1餐厅/2快递")
    private Integer industryType;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "经度")
    private BigDecimal longitude;

    @ApiModelProperty(value = "纬度")
    private BigDecimal latitude;

    @ApiModelProperty(value = "联系人")
    private String contact;

    @ApiModelProperty(value = "电话")
    private String telephone;

    @ApiModelProperty(value = "车辆数量")
    private Integer scooterQuantity;

    @ApiModelProperty(value = "证件类型1身份证，2驾驶证，3护照")
    private Integer certificateType;

    @ApiModelProperty(value = "证件正面附件")
    private String certificatePositiveAnnex;

    @ApiModelProperty(value = "证件反面附件")
    private String certificateNegativeAnnex;

    @ApiModelProperty(value = "营业执照编号")
    private String businessLicenseNum;

    @ApiModelProperty(value = "营业执照附件")
    private String businessLicenseAnnex;

    @ApiModelProperty(value = "发票编号")
    private String invoiceNum;

    @ApiModelProperty(value = "发票附件")
    private String invoiceAnnex;

    @ApiModelProperty(value = "合同附件")
    private String contractAnnex;

}
