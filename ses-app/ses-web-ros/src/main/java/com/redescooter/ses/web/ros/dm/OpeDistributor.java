package com.redescooter.ses.web.ros.dm;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chris
 * @since 2020-12-16
 */
@ApiModel(value = "经销商表")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@JsonInclude(value = Include.NON_NULL)
public class OpeDistributor implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.UUID)
    private Long id;

    @ApiModelProperty(value = "是否删除 0正常 1删除")
    @TableLogic
    private Integer dr;

    @ApiModelProperty(value = "租户id")
    private Long tenantId;

    @ApiModelProperty(value = "部门ID")
    private Long deptId;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "状态 1启用中 2未启用")
    private Integer status;

    @ApiModelProperty(value = "门店编码")
    private String code;

    @ApiModelProperty(value = "门店名称")
    private String name;

    @ApiModelProperty(value = "门店logo")
    private String logoUrl;

    @ApiModelProperty(value = "国家代码 86中国 33法国")
    private String countryCode;

    @ApiModelProperty(value = "电话")
    private String tel;

    @ApiModelProperty(value = "邮件地址")
    private String email;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "经度")
    private String longitude;

    @ApiModelProperty(value = "纬度")
    private String latitude;

    @ApiModelProperty(value = "邮编")
    private String cp;

    @ApiModelProperty(value = "城市")
    private String city;

    @ApiModelProperty(value = "地区")
    private String area;

    @ApiModelProperty(value = "合同url")
    private String contractUrl;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "门店类型 1销售 2维修 若多选通过逗号分隔")
    private String type;

    @ApiModelProperty(value = "门店类型是销售可售卖的产品 若多选通过逗号分隔")
    private String saleProduct;

    @ApiModelProperty(value = "创建人")
    private Long createdBy;

    @ApiModelProperty(value = "创建时间", hidden = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdTime;

    @ApiModelProperty(value = "修改人")
    private Long updatedBy;

    @ApiModelProperty(value = "修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedTime;

    @ApiModelProperty(value = "冗余字段1")
    private String def1;

    @ApiModelProperty(value = "冗余字段2")
    private String def2;

    @ApiModelProperty(value = "冗余字段3")
    private String def3;

    @ApiModelProperty(value = "冗余字段4")
    private String def4;

    @ApiModelProperty(value = "冗余字段5")
    private String def5;

}