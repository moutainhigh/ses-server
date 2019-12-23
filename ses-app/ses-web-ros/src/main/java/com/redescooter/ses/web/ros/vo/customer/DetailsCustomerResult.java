package com.redescooter.ses.web.ros.vo.customer;

import com.baomidou.mybatisplus.annotation.TableField;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 19/12/2019 10:04 上午
 * @ClassName: DetailsCustomerResult
 * @Function: TODO
 */
@ApiModel(value = "客户信息", description = "客户信息")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class DetailsCustomerResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "逻辑删除标识,0正常 1删除")
    private Integer dr;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "租户id")
    private Long tenantId;

    @ApiModelProperty(value = "时区")
    private String timeZone;

    @ApiModelProperty(value = "国家")
    private Long country;

    @ApiModelProperty(value = "国家名称")
    private String countryName;

    @ApiModelProperty(value = "城市")
    private Long city;

    @ApiModelProperty(value = "城市名称")
    private String cityName;

    @ApiModelProperty(value = "区域")
    private Long distrust;

    @ApiModelProperty(value = "区域名称")
    private String distrustName;

    @ApiModelProperty(value = "备注信息")
    private String memo;

    @ApiModelProperty(value = "销售")
    private Long salesId;

    @ApiModelProperty(value = "客户名字")
    private String customerFirstName;

    @ApiModelProperty(value = "客户姓氏")
    private String customerLastName;

    @ApiModelProperty(value = "客户全名")
    private String customerFullName;

    @ApiModelProperty(value = "企业名称")
    private String companyName;

    @ApiModelProperty(value = "客户来源渠道,SYSTEM-系统-1, WEBSITE-官网-2")
    private String customerSource;

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

    @ApiModelProperty(value = "邮件")
    private String email;

    @ApiModelProperty(value = "车辆数量")
    private Integer scooterQuantity;

    @ApiModelProperty(value = "证件类型:ID_CARD-身份证-1,DRIVER_LICENSE-驾驶证2，PASSPORT-护照-3")
    private String certificateType;

    @ApiModelProperty(value = "证件正面图片")
    private String certificatePositiveAnnex;

    @ApiModelProperty(value = "证件反面图片")
    private String certificateNegativeAnnex;

    @ApiModelProperty(value = "营业执照编号")
    private String businessLicenseNum;

    @ApiModelProperty(value = "营业执照图片")
    private String businessLicenseAnnex;

    @ApiModelProperty(value = "发票编号")
    private String invoiceNum;

    @ApiModelProperty(value = "发票附件")
    private String invoiceAnnex;

    @ApiModelProperty(value = "合同附件")
    private String contractAnnex;

    @ApiModelProperty(value = "账号使用标识，激活使用-1，未激活未使用-2")
    private Integer accountFlag;

    @ApiModelProperty(value = "创建人名称")
    private String createdName;

    @ApiModelProperty(value = "创建人")
    private Long createdBy;

    @ApiModelProperty(value = "创建时间")
    private Date createdTime;

    @ApiModelProperty(value = "更新人名称")
    private String updatedName;

    @ApiModelProperty(value = "更新人")
    private Long updatedBy;

    @ApiModelProperty(value = "更新时间")
    private Date updatedTime;


}
