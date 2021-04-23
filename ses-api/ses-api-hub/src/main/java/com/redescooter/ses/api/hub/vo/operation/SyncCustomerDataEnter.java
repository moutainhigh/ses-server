package com.redescooter.ses.api.hub.vo.operation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description
 * @Author Chris
 * @Date 2021/3/17 13:49
 */
@Data
public class SyncCustomerDataEnter {

    @ApiModelProperty(value = "逻辑删除标识 0正常 1删除")
    private Integer dr;

    @ApiModelProperty(value = "时区")
    private String timeZone;

    @ApiModelProperty(value = "国家")
    private String def1;

    @ApiModelProperty(value = "国家编码，如手机号 中国 +86")
    private String countryCode;

    @ApiModelProperty(value = "城市")
    private String def2;

    @ApiModelProperty(value = "区域")
    private String def3;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "销售")
    private Long salesId;

    @ApiModelProperty(value = "客户编码")
    private String customerCode;

    @ApiModelProperty(value = "客户名字")
    private String customerFirstName;

    @ApiModelProperty(value = "客户姓氏")
    private String customerLastName;

    @ApiModelProperty(value = "客户全名")
    private String customerFullName;

    @ApiModelProperty(value = "企业名称")
    private String companyName;

    @ApiModelProperty(value = "客户头像")
    private String picture;

    @ApiModelProperty(value = "客户来源渠道 官网/email/电话")
    private String customerSource;

    @ApiModelProperty(value = "客户类型 1企业/2个人")
    private String customerType;

    @ApiModelProperty(value = "客户行业类型，1餐厅/2快递")
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

    @ApiModelProperty(value = "备注信息")
    private String memo;

    @ApiModelProperty(value = "车辆数量")
    private Integer scooterQuantity;

    @ApiModelProperty(value = "已分配车辆数")
    private Integer assignationScooterQty;

    @ApiModelProperty(value = "证件类型1身份证，2驾驶证，3护照")
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

    @ApiModelProperty(value = "删除说明")
    private String description;

    @ApiModelProperty(value = "账号使用标识，即激活使用过1，未激活未使用0")
    private String accountFlag;

    @ApiModelProperty(value = "创建人")
    private Long createdBy;

    @ApiModelProperty(value = "创建时间")
    private Date createdTime;

    @ApiModelProperty(value = "更新人")
    private Long updatedBy;

    @ApiModelProperty(value = "更新时间")
    private Date updatedTime;

}
