package com.redescooter.ses.web.ros.vo.customer;

import com.baomidou.mybatisplus.annotation.TableField;
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

    @ApiModelProperty(value = "城市")
    private Long city;

    @ApiModelProperty(value = "区域")
    private Long distrust;

    @ApiModelProperty(value = "邮箱")
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

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "地点编号")
    private String placeId;

    @ApiModelProperty(value = "经度")
    private BigDecimal longitude;

    @ApiModelProperty(value = "纬度")
    private BigDecimal latitude;

    @ApiModelProperty(value = "联系人名字")
    private String contactFirstName;

    @ApiModelProperty(value = "联系人姓氏")
    private String contactLastName;

    @ApiModelProperty(value = "联系人全名")
    private String contactFullName;

    @ApiModelProperty(value = "电话")
    private String telephone;

    @ApiModelProperty(value = "车辆数量")
    private Integer scooterQuantity;

    @ApiModelProperty(value = "证件类型1身份证，2驾驶证，3护照")
    private String certificateType;

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
