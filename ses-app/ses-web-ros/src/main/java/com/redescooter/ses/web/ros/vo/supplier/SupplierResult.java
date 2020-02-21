package com.redescooter.ses.web.ros.vo.supplier;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@ApiModel(value = "采购商创建入参", description = "采购商创建")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class SupplierResult extends GeneralResult {
    /**
     * 主键
     */
    @ApiModelProperty(value="主键")
    private Long id;

    /**
     * 状态
     */
    @ApiModelProperty(value="状态")
    private String status;

    /**
     * 采购商名称
     */
    @ApiModelProperty(value="采购商名称")
    private String supplierName;

    /**
     * 采购商地址
     */
    @ApiModelProperty(value="采购商地址")
    private String supplierAddress;

    /**
     * 采购商国家
     */
    @ApiModelProperty(value = "采购商国家，与手机号归属国家对应")
    private String supplierCountry;

    /**
     * 采购商经度
     */
    @ApiModelProperty(value="采购商经度")
    private String supplierLongitude;

    /**
     * 采购商纬度
     */
    @ApiModelProperty(value="采购商纬度")
    private String supplierLatitude;

    /**
     * 采购商标签
     */
    @ApiModelProperty(value="采购商标签")
    private String supplierTag;

    /**
     * 采购商备注
     */
    @ApiModelProperty(value="采购商备注")
    private String supplierMemo;

    /**
     * 联系人名字
     */
    @ApiModelProperty(value="联系人名字")
    private String contactFirstName;

    /**
     * 联系人姓氏
     */
    @ApiModelProperty(value="联系人姓氏")
    private String contactLastName;

    /**
     * 联系人全名
     */
    @ApiModelProperty(value="联系人全名")
    private String contactFullName;

    /**
     * 联系人邮箱
     */
    @ApiModelProperty(value="联系人邮箱")
    private String contactEmail;
    /**
     * 国家编码，如手机号 中国 +86
     */
    @ApiModelProperty(value = "国家编码，如手机号 中国 +86")
    private String contactPhoneCountryCode;

    /**
     * 联系人手机号
     */
    @ApiModelProperty(value="联系人手机号")
    private String contactPhone;

    /**
     * 付款周期
     */
    @ApiModelProperty(value="付款周期")
    private Integer paymentCycle;

    /**
     * 合作开始时间
     */
    @ApiModelProperty(value="合作开始时间")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="UTC")
    private Date cooperationTimeStart;

    /**
     * 合作结束时间
     */
    @ApiModelProperty(value="合作结束时间")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="UTC")
    private Date cooperationTimeEnd;

    /**
     * 营业执照编号
     */
    @ApiModelProperty(value="营业执照编号")
    private String businessNumber;

    /**
     * 营业执照附件
     */
    @ApiModelProperty(value="营业执照附件")
    private String businessLicenseAnnex;

    /**
     * 合同编号
     */
    @ApiModelProperty(value="合同编号")
    private String contractNumber;

    /**
     * 合同附件
     */
    @ApiModelProperty(value="合同附件")
    private String contractAnnex;

    /**
     * 委托单数据量
     */
    @ApiModelProperty(value="委托单数据量")
    private int entrustOrdersNumber;

    /**
     * 完成订单数量
     */
    @ApiModelProperty(value="完成订单数量")
    private int completedOrdersNumber;

    /**
     * 质检成功率
     */
    @ApiModelProperty(value="质检成功率")
    private double fpy;

    /**
     * 不良率
     */
    @ApiModelProperty(value="不良率")
    private double rejectRatio;

    /**
     * 创建人
     */
    @ApiModelProperty(value="创建人")
    private Long createdBy;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="UTC")
    private Date createdTime;

    /**
     * 更新人
     */
    @ApiModelProperty(value="更新人")
    private Long updatedBy;

    /**
     * 更新时间
     */
    @ApiModelProperty(value="更新时间")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="UTC")
    private Date updatedTime;


}
