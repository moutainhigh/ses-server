package com.redescooter.ses.web.ros.dm;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 中国生产仓库出库单
 */
@ApiModel(value="com-redescooter-ses-web-ros-dm-OpeOutwhOrder")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "ope_outwh_order")
public class OpeOutwhOrder implements Serializable {
    /**
     * 主键 主键
     */
    @TableId(value = "id")
    @ApiModelProperty(value="主键 主键")
    private Long id;

    /**
     * 逻辑删除标识 逻辑删除标识
     */
    @TableField(value = "dr")
    @ApiModelProperty(value="逻辑删除标识 逻辑删除标识")
    @TableLogic
    private Integer dr;

    /**
     * 订单编号
     */
    @TableField(value = "order_no")
    @ApiModelProperty(value="订单编号")
    private String orderNo;

    /**
     * 物流编号
     */
    @TableField(value = "tracking_num")
    @ApiModelProperty(value="物流编号")
    private String trackingNum;

    /**
     * 入库仓库Id
     */
    @TableField(value = "in_wh_id")
    @ApiModelProperty(value="入库仓库Id")
    private Long inWhId;

    /**
     * 收货地址
     */
    @TableField(value = "address")
    @ApiModelProperty(value="收货地址")
    private String address;

    /**
     * 收货人Id（userId）
     */
    @TableField(value = "consignee_id")
    @ApiModelProperty(value="收货人Id（userId）")
    private Long consigneeId;

    /**
     * 收货人电话
     */
    @TableField(value = "consignee_phone")
    @ApiModelProperty(value="收货人电话")
    private String consigneePhone;

    /**
     * 电话国家代码
     */
    @TableField(value = "country_code")
    @ApiModelProperty(value="电话国家代码")
    private String countryCode;

    /**
     * 收货人邮箱
     */
    @TableField(value = "consignee_email")
    @ApiModelProperty(value="收货人邮箱")
    private String consigneeEmail;

    /**
     * 通知人姓名
     */
    @TableField(value = "notify_first_name")
    @ApiModelProperty(value="通知人姓名")
    private String notifyFirstName;

    /**
     * 通知人姓名
     */
    @TableField(value = "notify_last_name")
    @ApiModelProperty(value="通知人姓名")
    private String notifyLastName;

    /**
     * 物流方式
     */
    @TableField(value = "consign_type")
    @ApiModelProperty(value="物流方式")
    private String consignType;

    /**
     * 委托方式
     */
    @TableField(value = "consign_method")
    @ApiModelProperty(value="委托方式")
    private String consignMethod;

    /**
     * 物流公司
     */
    @TableField(value = "consign_company")
    @ApiModelProperty(value="物流公司")
    private String consignCompany;

    /**
     * 发货发票
     */
    @TableField(value = "annex")
    @ApiModelProperty(value="发货发票")
    private String annex;

    /**
     * 状态
     */
    @TableField(value = "`status`")
    @ApiModelProperty(value = "状态")
    private String status;

    /**
     * 产品总数
     */
    @TableField(value = "product_count")
    @ApiModelProperty(value="产品总数")
    private Integer productCount;

    /**
     * 物流价格
     */
    @TableField(value = "logistics_price")
    @ApiModelProperty(value="物流价格")
    private BigDecimal logisticsPrice;

    /**
     * 创建人
     */
    @TableField(value = "created_by")
    @ApiModelProperty(value="创建人")
    private Long createdBy;

    /**
     * 创建时间
     */
    @TableField(value = "created_time")
    @ApiModelProperty(value="创建时间")
    private Date createdTime;

    /**
     * 更新人
     */
    @TableField(value = "updated_by")
    @ApiModelProperty(value="更新人")
    private Long updatedBy;

    /**
     * 更新时间
     */
    @TableField(value = "updated_time")
    @ApiModelProperty(value="更新时间")
    private Date updatedTime;

    /**
     * 乐观锁
     */
    @TableField(value = "revision")
    @ApiModelProperty(value="乐观锁")
    private Integer revision;

    /**
     * 冗余字段
     */
    @TableField(value = "def1")
    @ApiModelProperty(value="冗余字段")
    private String def1;

    /**
     * 冗余字段
     */
    @TableField(value = "def2")
    @ApiModelProperty(value="冗余字段")
    private String def2;

    /**
     * 冗余字段
     */
    @TableField(value = "def3")
    @ApiModelProperty(value="冗余字段")
    private String def3;

    /**
     * 冗余字段
     */
    @TableField(value = "def5")
    @ApiModelProperty(value="冗余字段")
    private String def5;

    /**
     * 冗余字段
     */
    @TableField(value = "def6")
    @ApiModelProperty(value="冗余字段")
    private Double def6;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_DR = "dr";

    public static final String COL_ORDER_NO = "order_no";

    public static final String COL_TRACKING_NUM = "tracking_num";

    public static final String COL_IN_WH_ID = "in_wh_id";

    public static final String COL_ADDRESS = "address";

    public static final String COL_CONSIGNEE_ID = "consignee_id";

    public static final String COL_CONSIGNEE_PHONE = "consignee_phone";

    public static final String COL_COUNTRY_CODE = "country_code";

    public static final String COL_CONSIGNEE_EMAIL = "consignee_email";

    public static final String COL_NOTIFY_FIRST_NAME = "notify_first_name";

    public static final String COL_NOTIFY_LAST_NAME = "notify_last_name";

    public static final String COL_CONSIGN_TYPE = "consign_type";

    public static final String COL_CONSIGN_METHOD = "consign_method";

    public static final String COL_CONSIGN_COMPANY = "consign_company";

    public static final String COL_ANNEX = "annex";

    public static final String COL_STATUS = "status";

    public static final String COL_PRODUCT_COUNT = "product_count";

    public static final String COL_LOGISTICS_PRICE = "logistics_price";

    public static final String COL_CREATED_BY = "created_by";

    public static final String COL_CREATED_TIME = "created_time";

    public static final String COL_UPDATED_BY = "updated_by";

    public static final String COL_UPDATED_TIME = "updated_time";

    public static final String COL_REVISION = "revision";

    public static final String COL_DEF1 = "def1";

    public static final String COL_DEF2 = "def2";

    public static final String COL_DEF3 = "def3";

    public static final String COL_DEF5 = "def5";

    public static final String COL_DEF6 = "def6";
}
