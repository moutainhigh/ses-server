package com.redescooter.ses.api.hub.vo.operation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description
 * @Author Chris
 * @Date 2021/4/19 10:34
 */
@Data
public class SyncContactUsDataEnter implements Serializable {

    /**
     * 删除标志
     */
    @ApiModelProperty(value = "删除标志")
    private Integer dr;

    /**
     * 客户邮箱
     */
    @ApiModelProperty(value = "客户邮箱")
    private String email;

    /**
     * 名
     */
    @ApiModelProperty(value = "名")
    private String firstName;

    /**
     * 姓
     */
    @ApiModelProperty(value = "姓")
    private String lastName;

    /**
     * 全名
     */
    @ApiModelProperty(value = "全名")
    private String fullName;

    /**
     * 电话
     */
    @ApiModelProperty(value = "电话")
    private String telephone;

    /**
     * 国家Id
     */
    @ApiModelProperty(value = "国家Id")
    private Long country;

    /**
     * 国家代码
     */
    @ApiModelProperty(value = "国家代码")
    private String countryCode;

    /**
     * 国家名称
     */
    @ApiModelProperty(value = "国家名称")
    private String countryName;

    /**
     * 城市名称
     */
    @ApiModelProperty(value = "城市名称")
    private String cityName;

    /**
     * 区域Id
     */
    @ApiModelProperty(value = "区域Id")
    private Long district;

    /**
     * 区域编码
     */
    @ApiModelProperty(value = "区域编码")
    private String districtName;

    /**
     * 地址
     */
    @ApiModelProperty(value = "地址")
    private String address;

    /**
     * 联系次数
     */
    @ApiModelProperty(value = "联系次数")
    private Integer frequency;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private String status;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 乐观锁
     */
    @ApiModelProperty(value = "乐观锁")
    private Integer revision;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private Long createdBy;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createdTime;

    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    private Long updatedBy;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private Date updatedTime;


}
