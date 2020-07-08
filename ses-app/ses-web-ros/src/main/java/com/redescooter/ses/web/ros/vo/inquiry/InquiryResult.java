package com.redescooter.ses.web.ros.vo.inquiry;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @ClassName:InquiryResult
 * @description: InquiryResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/05 15:12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class InquiryResult extends GeneralResult {

    @ApiModelProperty(value = "客户名字")
    private Long id;

    @ApiModelProperty(value = "公司名字")
    private String companyName;

    @ApiModelProperty(value = "客户名字")
    private String customerFirstName;

    @ApiModelProperty(value = "客户姓氏")
    private String customerLastName;

    @ApiModelProperty(value = "客户全名")
    private String customerFullName;

    @ApiModelProperty(value = "联系人名字")
    private String contactFirstName;

    @ApiModelProperty(value = "联系人姓氏")
    private String contactLastName;

    @ApiModelProperty(value = "联系人姓氏")
    private String contactFullName;

    @ApiModelProperty(value = "联系人姓氏")
    private String email;

    @ApiModelProperty(value = "联系人姓氏")
    private String telephone;

    @ApiModelProperty(value = "国家代码")
    private String countryCode;

    @ApiModelProperty(value = "城市")
    private Long cityId;

    @ApiModelProperty(value = "城市名称")
    private String cityName;

    @ApiModelProperty(value = "区域")
    private Long distrustId;

    @ApiModelProperty(value = "区域名称")
    private String distrustName;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "客户类型")
    private String customerType;

    @ApiModelProperty(value = "行业类型")
    private String industryType;

    @ApiModelProperty(value = "车辆数量")
    private Integer scooterQty;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "单据来源")
    private String source;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "接受时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date acceptanceTime;

    @ApiModelProperty(value = "剩余时间")
    private Long ttl;

    @ApiModelProperty(value = "区域")
    private String def2;
}
