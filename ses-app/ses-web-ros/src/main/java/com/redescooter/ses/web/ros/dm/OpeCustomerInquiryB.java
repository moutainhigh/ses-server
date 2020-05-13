package com.redescooter.ses.web.ros.dm;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "ope_customer_inquiry_b")
public class OpeCustomerInquiryB implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    /**
     * 逻辑删除标识 0正常 1删除
     */
    @TableField(value = "dr")
    private Integer dr;

    /**
     * 询价单Id
     */
    @TableField(value = "inquiry_id")
    private Long inquiryId;

    /**
     * 配件表
     */
    @TableField(value = "accessory_id")
    private Long accessoryId;

    /**
     * 配件价格
     */
    @TableField(value = "accessory_price")
    private BigDecimal accessoryPrice;

    /**
     * 配件类型
     */
    @TableField(value = "accessory_type")
    private String accessoryType;

    /**
     * 配件数量
     */
    @TableField(value = "accessory_qty")
    private Integer accessoryQty;

    /**
     * 产品Id
     */
    @TableField(value = "product_id")
    private Long productId;

    /**
     * 创建人
     */
    @TableField(value = "created_by")
    private Long createdBy;

    /**
     * 创建时间
     */
    @TableField(value = "created_time")
    private Date createdTime;

    /**
     * 更新人
     */
    @TableField(value = "updated_by")
    private Long updatedBy;

    /**
     * 更新时间
     */
    @TableField(value = "updated_time")
    private Date updatedTime;

    /**
     * 冗余字段
     */
    @TableField(value = "def1")
    private String def1;

    /**
     * 冗余字段
     */
    @TableField(value = "def2")
    private String def2;

    /**
     * 冗余字段
     */
    @TableField(value = "def3")
    private String def3;

    /**
     * 冗余字段
     */
    @TableField(value = "def5")
    private String def5;

    /**
     * 冗余字段
     */
    @TableField(value = "def6")
    private Double def6;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_DR = "dr";

    public static final String COL_INQUIRY_ID = "inquiry_id";

    public static final String COL_ACCESSORY_ID = "accessory_id";

    public static final String COL_ACCESSORY_PRICE = "accessory_price";

    public static final String COL_ACCESSORY_TYPE = "accessory_type";

    public static final String COL_ACCESSORY_QTY = "accessory_qty";

    public static final String COL_PRODUCT_ID = "product_id";

    public static final String COL_CREATED_BY = "created_by";

    public static final String COL_CREATED_TIME = "created_time";

    public static final String COL_UPDATED_BY = "updated_by";

    public static final String COL_UPDATED_TIME = "updated_time";

    public static final String COL_DEF1 = "def1";

    public static final String COL_DEF2 = "def2";

    public static final String COL_DEF3 = "def3";

    public static final String COL_DEF5 = "def5";

    public static final String COL_DEF6 = "def6";

    public static OpeCustomerInquiryBBuilder builder() {
        return new OpeCustomerInquiryBBuilder();
    }
}