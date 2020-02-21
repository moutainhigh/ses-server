package com.redescooter.ses.web.ros.vo.factory;

import com.baomidou.mybatisplus.annotation.TableField;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;

@ApiModel(value = "代工厂创建入参", description = "代工厂创建")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class FactorySaveEnter  extends GeneralEnter {

    /**
     * 状态
     */
    @ApiModelProperty(value="状态")
    private String status;

    /**
     * 代工厂名称
     */
    @ApiModelProperty(value="代工厂名称")
    private String factoryName;

    /**
     * 代工厂地址
     */
    @ApiModelProperty(value="代工厂地址")
    private String factoryAddress;

    /**
     * 代工厂国家
     */
    @ApiModelProperty(value = "代工厂国家，与手机号归属国家对应")
    private String factoryCountry;

    /**
     * 代工厂经度
     */
    @ApiModelProperty(value="代工厂经度")
    private Long factoryLongitude;

    /**
     * 代工厂纬度
     */
    @ApiModelProperty(value="代工厂纬度")
    private Long factoryLatitude;

    /**
     * 代工厂标签
     */
    @ApiModelProperty(value="代工厂标签")
    private String factoryTag;

    /**
     * 代工厂备注
     */
    @ApiModelProperty(value="代工厂备注")
    private String factoryMemo;

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
    @TableField(value = "country_code")
    @ApiModelProperty(value = "国家编码，如手机号 中国 +86")
    private String countryCode;

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
    private Date cooperationTimeStart;

    /**
     * 合作结束时间
     */
    @ApiModelProperty(value="合作结束时间")
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



}
