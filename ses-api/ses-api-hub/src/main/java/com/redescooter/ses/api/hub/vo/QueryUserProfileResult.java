package com.redescooter.ses.api.hub.vo;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @ClassName:QueryUserProfileResult
 * @description: QueryUserProfileResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/01 19:48
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = true)
public class QueryUserProfileResult extends GeneralResult {
    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "租户ID")
    private Long tenantId;

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "姓")
    private String firstName;

    @ApiModelProperty(value = "名")
    private String lastName;

    @ApiModelProperty(value = "姓名")
    private String fullName;

    @ApiModelProperty(value = "照片")
    private String picture;

    @ApiModelProperty(value = "邮箱")
    private String email1;

    @ApiModelProperty(value = "国家编码如+86")
    private String countryCode1;

    @ApiModelProperty(value = "电话号")
    private String telNumber1;

    @ApiModelProperty(value = "性别")
    private String gender;

    @ApiModelProperty(value = "生日")
    private Date birthday;

    @ApiModelProperty(value = "证件类型1身份证，2驾驶证，3护照")
    private String certificateType;

    @ApiModelProperty(value = "证件反面图片")
    private String certificateNegativeAnnex;

    @ApiModelProperty(value = "证件正面图片")
    private String certificatePositiveAnnex;

    @ApiModelProperty(value = "出生地")
    private String placeBirth;

    @ApiModelProperty(value = "居住地址")
    private String address;


    // 合同、发票 两个字段只有TOC使用
    @ApiModelProperty(value = "发票编号 ToC 使用")
    private String invoiceNum;

    @ApiModelProperty(value = "发票附件 ToC 使用")
    private String invoiceAnnex;

    @ApiModelProperty(value = "合同附件 ToC 使用")
    private String contractAnnex;
}
