package com.redescooter.ses.web.ros.vo.inquiry;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @ClassNameInquiryExportResult
 * @Description
 * @Author Aleks
 * @Date2020/9/30 15:40
 * @Version V1.0
 **/
@Data
public class InquiryExportResult {

    @ApiModelProperty("名称")
    private String customerFullName;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("银行卡名称")
    private String bankCardName;

    @ApiModelProperty("区域")
    private String postcode;

    @ApiModelProperty("地址")
    private String address;

    @ApiModelProperty("产品名称")
    private String productName;

    @ApiModelProperty("颜色")
    private String colorName;

    @ApiModelProperty("电池数量")
    private Integer batteryQty;

    @ApiModelProperty("尾款")
    private Double balance;

    @ApiModelProperty("已付金额")
    private Double amountPaid;

    @ApiModelProperty("总金额")
    private Double totalPrice;

    @ApiModelProperty("创建时间")
    private Date createdTime;

}
