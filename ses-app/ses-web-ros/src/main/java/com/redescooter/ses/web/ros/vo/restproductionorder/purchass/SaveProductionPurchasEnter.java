package com.redescooter.ses.web.ros.vo.restproductionorder.purchass;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.annotation.MaximumLength;
import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.annotation.Regexp;
import com.redescooter.ses.api.common.exception.ValidationExceptionBaseCode;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.ros.exception.ValidationExceptionCode;
import lombok.*;

import java.util.Date;

import io.swagger.annotations.*;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @ClassName:SaveProductionPurchasEnter
 * @description: SaveProductionPurchasEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/11/10 16:08 
 */
@ApiModel(value = "保存采购接口", description = "保存采购接口")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
public class SaveProductionPurchasEnter extends GeneralEnter {

    @ApiModelProperty(value = "供应商Id")
    @NotNull(code = ValidationExceptionBaseCode.ID_IS_EMPTY, message = "Id 为空")
    private Long factoryId;

    @ApiModelProperty(value = "交货日期")
    @NotNull(code = ValidationExceptionCode.DATE_IS_NOT_EMPTY, message = "日期为空")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date deliveryDate;

    @ApiModelProperty(value = "负责人")
    private Long factoryPrincipalId;

    @ApiModelProperty(value = "负责人")
    @NotNull(code = ValidationExceptionCode.NAME_IS_EMPTY,message = "负责人姓名为空")
    @Regexp(code = ValidationExceptionCode.NAME_IS_ILLEGAL,message = "负责人姓名非法")
    private String factoryPrincipalName;

    @ApiModelProperty(value = "负责人国家代码")
    @NotNull(code = ValidationExceptionCode.COUNTRY_CODE_IS_EMPTY,message = "负责人区域代码为空")
    private String principalCountryCode;

    @ApiModelProperty(value = "负责人电话")
    @NotNull(code = ValidationExceptionCode.TELEPHONE_IS_NUMBER,message = "负责人电话为空")
    private String principalTelephone;

    @ApiModelProperty(value = "采购人对接人Id")
    private Long dockingUser;

    @ApiModelProperty(value = "采购人对接人名称")
    @NotNull(code = ValidationExceptionCode.NAME_IS_EMPTY,message = "采购人对接人姓名为空")
    @Regexp(code = ValidationExceptionCode.NAME_IS_ILLEGAL,message = "采购人对接人姓名非法")
    private String dockingUserName;

    @ApiModelProperty(value = "采购人对接人国家代码")
    @NotNull(code = ValidationExceptionCode.COUNTRY_CODE_IS_EMPTY,message = "负责人区域代码为空")
    private String dockingCountryCode;

    @ApiModelProperty(value = "采购人对接人电话")
    @NotNull(code = ValidationExceptionCode.TELEPHONE_IS_NUMBER,message = "负责人电话为空")
    private String dockingUserTelephone;

    @ApiModelProperty(value = "收货人Id")
    private Long consigneeUser;

    @ApiModelProperty(value = "收货人姓名")
    @NotNull(code = ValidationExceptionCode.NAME_IS_EMPTY,message = "采购人对接人姓名为空")
    @Regexp(code = ValidationExceptionCode.NAME_IS_ILLEGAL,message = "采购人对接人姓名非法")
    private String consigneeUserName;

    @ApiModelProperty(value = "收货人国家代码")
    @NotNull(code = ValidationExceptionCode.COUNTRY_CODE_IS_EMPTY,message = "负责人区域代码为空")
    private String consigneeCountryCode;

    @ApiModelProperty(value = "收货人电话")
    @NotNull(code = ValidationExceptionCode.TELEPHONE_IS_NUMBER,message = "负责人电话为空")
    private String consigneeUserTelephone;

    @ApiModelProperty(value = "收货人地址")
    @NotNull(code = ValidationExceptionCode.ADDRESS_INFO_IS_EMPTY,message = "收货人地址为空")
    @MaximumLength(value = "200",code = ValidationExceptionCode.ADDRESS_CHAR_IS_NOT_ILLEGAL,message = "收货人地址过长")
    private String consigneeAddress;

    @ApiModelProperty(value = "备注")
    @MaximumLength(value = "200",code = ValidationExceptionCode.REMARK_ILLEGAL,message = "备注过长")
    private String remark;

    @ApiModelProperty(value = "付款数据")
    @NotNull(code = ValidationExceptionCode.PAYMENTINFO_IS_EMPTY, message = "付款信息为空")
    private String paymentDate;

    @ApiModelProperty(value = "产品列表")
    @NotNull(code = ValidationExceptionCode.PRODUCT_IS_EMPTY,message = "产品列表为空")
    private String st;

    @ApiModelProperty(value = "合同")
    @NotNull(code = ValidationExceptionCode.CONTRACT_ANNEX_IS_EMPTY,message = "合同为空")
    private String contract;
}
