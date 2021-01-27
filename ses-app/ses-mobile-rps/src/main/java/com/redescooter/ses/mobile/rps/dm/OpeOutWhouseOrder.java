package com.redescooter.ses.mobile.rps.dm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author assert
 * @date 2021/1/27 22:52
 */
@ApiModel(value = "com-redescooter-ses-mobile-rps-dm-OpeOutWhouseOrder")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OpeOutWhouseOrder {
    public static final String COL_ID = "id";
    public static final String COL_DR = "dr";
    public static final String COL_TENANT_ID = "tenant_id";
    public static final String COL_DEPT_ID = "dept_id";
    public static final String COL_INVOICE_ID = "invoice_id";
    public static final String COL_INVOICE_NO = "invoice_no";
    public static final String COL_OUT_WH_NO = "out_wh_no";
    public static final String COL_OUT_WH_STATUS = "out_wh_status";
    public static final String COL_OUT_WH_TYPE = "out_wh_type";
    public static final String COL_OUT_TYPE = "out_type";
    public static final String COL_OUT_WH_QTY = "out_wh_qty";
    public static final String COL_ALREADY_OUT_WH_QTY = "already_out_wh_qty";
    public static final String COL_OUT_STOCK_TIME = "out_stock_time";
    public static final String COL_COUNTRY_CODE = "country_code";
    public static final String COL_TELEPHONE = "telephone";
    public static final String COL_MAIL = "mail";
    public static final String COL_REMARK = "remark";
    public static final String COL_CREATED_BY = "created_by";
    public static final String COL_CREATED_TIME = "created_time";
    public static final String COL_UPDATED_BY = "updated_by";
    public static final String COL_UPDATED_TIME = "updated_time";
    public static final String COL_DEF1 = "def1";
    public static final String COL_DEF2 = "def2";
    public static final String COL_DEF3 = "def3";
    public static final String COL_DEF4 = "def4";
    public static final String COL_DEF5 = "def5";
    private static final long serialVersionUID = 1L;
    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    private Long id;

    /**
     * 逻辑删除
     */
    @ApiModelProperty(value = "逻辑删除")
    private Integer dr;

    /**
     * 租户ID
     */
    @ApiModelProperty(value = "租户ID")
    private Long tenantId;

    /**
     * 部门id（做数据权限用）
     */
    @ApiModelProperty(value = "部门id（做数据权限用）")
    private Long deptId;

    /**
     * 国家类型，1:中国，2:法国
     */
    @ApiModelProperty(value = "国家类型，1:中国，2:法国")
    private Integer countryType;

    /**
     * 关联的单据id
     */
    @ApiModelProperty(value = "关联的单据id")
    private Long relationId;

    /**
     * 关联的单据号
     */
    @ApiModelProperty(value = "关联的单据号")
    private String relationNo;

    /**
     * 关联的单据类型，3：发货单，9：组装单，10:退换单
     */
    @ApiModelProperty(value = "关联的单据类型，3：发货单，9：组装单，10:退换单")
    private Integer relationType;

    /**
     * 出库仓库。1:成品库，2:原料库，3:不合格品库
     */
    @ApiModelProperty(value = "出库仓库。1:成品库，2:原料库，3:不合格品库")
    private Integer whType;

    /**
     * 出库单号
     */
    @ApiModelProperty(value = "出库单号")
    private String outWhNo;

    /**
     * 出库单状态 -1新建 10待出库 20已出库 30已取消
     */
    @ApiModelProperty(value = "出库单状态 -1新建 10待出库 20已出库 30已取消")
    private Integer outWhStatus;

    /**
     * 出库单类型，1：整车，2：组装件，3：部件
     */
    @ApiModelProperty(value = "出库单类型，1：整车，2：组装件，3：部件")
    private Integer outWhType;

    /**
     * 出库类型，1：调拨出库，2：组装备料出库，3：退换出库，4：其它，5:返修出库，6:退货出库，7:销售出库
     */
    @ApiModelProperty(value = "出库类型，1：调拨出库，2：组装备料出库，3：退换出库，4：其它，5:返修出库，6:退货出库，7:销售出库")
    private Integer outType;

    /**
     * 出库数量
     */
    @ApiModelProperty(value = "出库数量")
    private Integer outWhQty;

    /**
     * 已出库数量
     */
    @ApiModelProperty(value = "已出库数量")
    private Integer alreadyOutWhQty;

    /**
     * 出库时间
     */
    @ApiModelProperty(value = "出库时间")
    private Date outStockTime;

    /**
     * 国家编码如+86
     */
    @ApiModelProperty(value = "国家编码如+86")
    private String countryCode;

    /**
     * 联系人电话
     */
    @ApiModelProperty(value = "联系人电话")
    private String telephone;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    private String mail;

    /**
     * 是否是不合格品库产生，0:否，1:是
     */
    @ApiModelProperty(value = "是否是不合格品库产生，0:否，1:是")
    private Integer source;

    /**
     * 是否是仓库新增 0否 1是
     */
    @ApiModelProperty(value = "是否是仓库新增 0否 1是")
    private Integer ifWh;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

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

    /**
     * 冗余字段
     */
    @ApiModelProperty(value = "冗余字段")
    private String def1;

    /**
     * 冗余字段
     */
    @ApiModelProperty(value = "冗余字段")
    private String def2;

    /**
     * 冗余字段
     */
    @ApiModelProperty(value = "冗余字段")
    private String def3;

    /**
     * 冗余字段
     */
    @ApiModelProperty(value = "冗余字段")
    private String def4;

    /**
     * 冗余字段
     */
    @ApiModelProperty(value = "冗余字段")
    private BigDecimal def5;

    public static OpeOutWhouseOrderBuilder builder() {
        return new OpeOutWhouseOrderBuilder();
    }
}