package com.redescooter.ses.web.ros.vo.customer;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @ClassName: CustomerDetailsResult
 * @Function: TODO
 */
@ApiModel(value = "客户信息", description = "客户信息")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class CustomerDetailsResult extends GeneralResult {

    @ApiModelProperty(value="id")
    private Long id;

    @ApiModelProperty(value="逻辑删除标识 0正常 1删除")
    private Integer dr;

    @ApiModelProperty(value="租户id")
    private Long tenantId;

    @ApiModelProperty(value="时区")
    private String timeZone;

    @ApiModelProperty(value="国家")
    private Long country;

    @ApiModelProperty(value="城市")
    private Long city;

    @ApiModelProperty(value="区域")
    private Long distrust;

    @ApiModelProperty(value="状态")
    private String status;

    @ApiModelProperty(value="销售")
    private Long salesId;

    @ApiModelProperty(value="客户名")
    private String name;

    @ApiModelProperty(value="客户头像")
    private String picture;

    @ApiModelProperty(value="客户来源渠道 官网/email/电话")
    private String customerSource;

    private Integer customerType;

    @ApiModelProperty(value="客户行业类型，1餐厅/2快递")
    private Integer industryType;

    @ApiModelProperty(value="地址")
    private String address;

    @ApiModelProperty(value="经度")
    private BigDecimal longitude;

    @ApiModelProperty(value="纬度")
    private BigDecimal latitude;

    @ApiModelProperty(value="联系人")
    private String contact;

    @ApiModelProperty(value="电话")
    private String telephone;

    @ApiModelProperty(value="邮件")
    private String email;

    @ApiModelProperty(value="备注信息")
    private String memo;

    @ApiModelProperty(value="车辆数量")
    private Integer scooterQuantity;

    @ApiModelProperty(value="证件类型1身份证，2驾驶证，3护照")
    private Integer certificateType;

    @ApiModelProperty(value="证件正面图片")
    private String certificatePositiveAnnex;

    @ApiModelProperty(value="证件反面图片")
    private String certificateNegativeAnnex;

    @ApiModelProperty(value="营业执照编号")
    private String businessLicenseNum;

    @ApiModelProperty(value="营业执照图片")
    private String businessLicenseAnnex;

    @ApiModelProperty(value="发票编号")
    private String invoiceNum;

    @ApiModelProperty(value="发票附件")
    private String invoiceAnnex;

    @ApiModelProperty(value="合同附件")
    private String contractAnnex;

    @ApiModelProperty(value="账号使用标识，即激活使用过1，未激活未使用2")
    private Integer accountFlag;

    @ApiModelProperty(value="创建人")
    private Long createdBy;

    @ApiModelProperty(value="创建时间")
    private Date createdTime;

    @ApiModelProperty(value="更新人")
    private Long updatedBy;

    @ApiModelProperty(value="更新时间")
    private Date updatedTime;

}
