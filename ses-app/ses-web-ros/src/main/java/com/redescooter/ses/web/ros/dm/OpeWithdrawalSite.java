package com.redescooter.ses.web.ros.dm;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 取货地址
 */
@ApiModel(value = "ses-redescooter-com-ros-dm-OpeWithdrawalSite")
@Data
@TableName(value = "ope_withdrawal_site")
public class OpeWithdrawalSite implements Serializable {
    /**
     * 主建
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "主建")
    private Long id;

    /**
     * 逻辑删除标识 0正常 1删除
     */
    @TableField(value = "dr")
    @ApiModelProperty(value = "逻辑删除标识 0正常 1删除")
    @TableLogic
    private Integer dr;

    /**
     * 店铺状态 0正常 1冻结
     */
    @TableField(value = "`status`")
    @ApiModelProperty(value = "店铺状态 0正常 1冻结")
    private Integer status;

    /**
     * 租户ID
     */
    @TableField(value = "tenant_id")
    @ApiModelProperty(value = "租户ID")
    private Long tenantId;

    /**
     * 用户ID
     */
    @TableField(value = "user_id")
    @ApiModelProperty(value = "用户ID")
    private Long userId;

    /**
     * 营业状态【0开门，1关门】
     */
    @TableField(value = "business_status")
    @ApiModelProperty(value = "营业状态【0开门，1关门】")
    private Integer businessStatus;

    /**
     * 店铺类型【1维修，-1销售，0全部】
     */
    @TableField(value = "`type`")
    @ApiModelProperty(value = "店铺类型【1维修，-1销售，0全部】")
    private Integer type;

    /**
     * 街道编号
     */
    @TableField(value = "street_number")
    @ApiModelProperty(value = "街道编号")
    private String streetNumber;

    /**
     * 街道名称
     */
    @TableField(value = "street_name")
    @ApiModelProperty(value = "街道名称")
    private String streetName;

    /**
     * 联系人姓氏
     */
    @TableField(value = "contact_first")
    @ApiModelProperty(value = "联系人姓氏")
    private String contactFirst;

    /**
     * 联系人名字
     */
    @TableField(value = "contact_last")
    @ApiModelProperty(value = "联系人名字")
    private String contactLast;

    /**
     * 联系人全名
     */
    @TableField(value = "contant_full_name")
    @ApiModelProperty(value = "联系人全名")
    private Integer contantFullName;

    /**
     * 店铺邮箱
     */
    @TableField(value = "email")
    @ApiModelProperty(value = "店铺邮箱")
    private String email;

    /**
     * 店铺联系电话
     */
    @TableField(value = "phone_number")
    @ApiModelProperty(value = "店铺联系电话")
    private String phoneNumber;

    /**
     * 营业开始时间
     */
    @TableField(value = "open_time")
    @ApiModelProperty(value = "营业开始时间")
    private Date openTime;

    /**
     * 营业结束时间
     */
    @TableField(value = "close_time")
    @ApiModelProperty(value = "营业结束时间")
    private Date closeTime;

    /**
     * 以json的数据格式，key 和value的方式进行存储
     */
    @TableField(value = "other_params")
    @ApiModelProperty(value = "以json的数据格式，key 和value的方式进行存储")
    private String otherParams;

    /**
     * 门店编码
     */
    @TableField(value = "store_code")
    @ApiModelProperty(value = "门店编码")
    private String storeCode;

    /**
     * 门店名称
     */
    @TableField(value = "store_name")
    @ApiModelProperty(value = "门店名称")
    private String storeName;

    /**
     * 国家
     */
    @TableField(value = "country")
    @ApiModelProperty(value = "国家")
    private String country;

    /**
     * 城市
     */
    @TableField(value = "city")
    @ApiModelProperty(value = "城市")
    private String city;

    /**
     * 区域
     */
    @TableField(value = "area")
    @ApiModelProperty(value = "区域")
    private String area;

    /**
     * 邮政编码
     */
    @TableField(value = "code_postal")
    @ApiModelProperty(value = "邮政编码")
    private String codePostal;

    /**
     * 详细地址
     */
    @TableField(value = "address")
    @ApiModelProperty(value = "详细地址")
    private String address;

    /**
     * 备注
     */
    @TableField(value = "remarks")
    @ApiModelProperty(value = "备注")
    private String remarks;

    /**
     * 创建人
     */
    @TableField(value = "created_by")
    @ApiModelProperty(value = "创建人")
    private Long createdBy;

    /**
     * 创建时间
     */
    @TableField(value = "created_time")
    @ApiModelProperty(value = "创建时间")
    private Date createdTime;

    /**
     * 更新人
     */
    @TableField(value = "updated_by")
    @ApiModelProperty(value = "更新人")
    private Long updatedBy;

    /**
     * 更新时间
     */
    @TableField(value = "updated_time")
    @ApiModelProperty(value = "更新时间")
    private Date updatedTime;

    /**
     * 冗余字段
     */
    @TableField(value = "def1")
    @ApiModelProperty(value = "冗余字段")
    private String def1;

    /**
     * 冗余字段
     */
    @TableField(value = "def2")
    @ApiModelProperty(value = "冗余字段")
    private String def2;

    /**
     * 冗余字段
     */
    @TableField(value = "def3")
    @ApiModelProperty(value = "冗余字段")
    private String def3;

    /**
     * 冗余字段
     */
    @TableField(value = "def5")
    @ApiModelProperty(value = "冗余字段")
    private String def5;

    /**
     * 冗余字段
     */
    @TableField(value = "def6")
    @ApiModelProperty(value = "冗余字段")
    private Double def6;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_DR = "dr";

    public static final String COL_STATUS = "status";

    public static final String COL_TENANT_ID = "tenant_id";

    public static final String COL_USER_ID = "user_id";

    public static final String COL_BUSINESS_STATUS = "business_status";

    public static final String COL_TYPE = "type";

    public static final String COL_STREET_NUMBER = "street_number";

    public static final String COL_STREET_NAME = "street_name";

    public static final String COL_CONTACT_FIRST = "contact_first";

    public static final String COL_CONTACT_LAST = "contact_last";

    public static final String COL_CONTANT_FULL_NAME = "contant_full_name";

    public static final String COL_EMAIL = "email";

    public static final String COL_PHONE_NUMBER = "phone_number";

    public static final String COL_OPEN_TIME = "open_time";

    public static final String COL_CLOSE_TIME = "close_time";

    public static final String COL_OTHER_PARAMS = "other_params";

    public static final String COL_STORE_CODE = "store_code";

    public static final String COL_STORE_NAME = "store_name";

    public static final String COL_COUNTRY = "country";

    public static final String COL_CITY = "city";

    public static final String COL_AREA = "area";

    public static final String COL_CODE_POSTAL = "code_postal";

    public static final String COL_ADDRESS = "address";

    public static final String COL_REMARKS = "remarks";

    public static final String COL_CREATED_BY = "created_by";

    public static final String COL_CREATED_TIME = "created_time";

    public static final String COL_UPDATED_BY = "updated_by";

    public static final String COL_UPDATED_TIME = "updated_time";

    public static final String COL_DEF1 = "def1";

    public static final String COL_DEF2 = "def2";

    public static final String COL_DEF3 = "def3";

    public static final String COL_DEF5 = "def5";

    public static final String COL_DEF6 = "def6";
}