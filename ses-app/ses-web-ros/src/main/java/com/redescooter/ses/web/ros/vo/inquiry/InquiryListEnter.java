package com.redescooter.ses.web.ros.vo.inquiry;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @ClassName:InquiryListEnter
 * @description: InquiryListEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/05 15:06
 */
@ApiModel(value = "询价单列表", description = "询价单列表")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class InquiryListEnter extends PageEnter {

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "城市")
//    private Long cityId;
    private String cityName;

    @ApiModelProperty(value = "区域")
//    private Long distrustId;
    private String districtName;

    @ApiModelProperty(value = "国家")
//    private Long distrustId;
    private String countryName;

    @ApiModelProperty(value = "行业类型")
    private String industyType;

    @ApiModelProperty(value = "客户类型")
    private String customerType;

    @ApiModelProperty(value = "处理时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date acceptanceStartTime;

    @ApiModelProperty(value = "处理时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date acceptanceEndTime;

    @ApiModelProperty(value = "关键字")
    private String keyword;

    @ApiModelProperty(value = "来源1 询价单 2预订单")
    private String source;

}
