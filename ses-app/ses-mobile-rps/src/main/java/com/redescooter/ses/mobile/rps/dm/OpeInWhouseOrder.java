package com.redescooter.ses.mobile.rps.dm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 *@author assert
 *@date 2021/1/18 10:46
 */
@ApiModel(value="com-redescooter-ses-mobile-rps-dm-OpeInWhouseOrder")
@Data
public class OpeInWhouseOrder {
    /**
    * 主键id
    */
    @ApiModelProperty(value="主键id")
    private Long id;

    /**
    * 逻辑删除
    */
    @ApiModelProperty(value="逻辑删除")
    private Integer dr;

    /**
    * 租户ID
    */
    @ApiModelProperty(value="租户ID")
    private Integer tenantId;

    /**
    * 部门id（做数据权限用）
    */
    @ApiModelProperty(value="部门id（做数据权限用）")
    private Long deptId;

    /**
    * 国家类型，1:中国，2:法国
    */
    @ApiModelProperty(value="国家类型，1:中国，2:法国")
    private Integer countryType;

    /**
    * 入库单号
    */
    @ApiModelProperty(value="入库单号")
    private String inWhNo;

    /**
    * 入库单状态， 1： 草稿，:10：待质检，20：质检中，25：待入库，30：已入库
    */
    @ApiModelProperty(value="入库单状态， 1： 草稿，:10：待质检，20：质检中，25：待入库，30：已入库")
    private Integer inWhStatus;

    /**
    * 入库仓库。1:成品库，2:原料库，3:不合格品库
    */
    @ApiModelProperty(value="入库仓库。1:成品库，2:原料库，3:不合格品库")
    private Integer whType;

    /**
    * 入库单据类型,1:车辆，2:组装件，3:部件
    */
    @ApiModelProperty(value="入库单据类型,1:车辆，2:组装件，3:部件")
    private Integer orderType;

    /**
    * 关联的单据id
    */
    @ApiModelProperty(value="关联的单据id")
    private Long relationOrderId;

    /**
    * 关联的单据号
    */
    @ApiModelProperty(value="关联的单据号")
    private String relationOrderNo;

    /**
    * 关联的单据类型，7：生产采购单，9：组装单
    */
    @ApiModelProperty(value="关联的单据类型，7：生产采购单，9：组装单")
    private Integer relationOrderType;

    /**
    * 入库类型，1：生产入库，2：返修入库，3：采购入库，4：退料入库，5：其他，6:报废入库，7:调拨入库
    */
    @ApiModelProperty(value="入库类型，1：生产入库，2：返修入库，3：采购入库，4：退料入库，5：其他，6:报废入库，7:调拨入库")
    private Integer inWhType;

    /**
    * 入库数量
    */
    @ApiModelProperty(value="入库数量")
    private Integer inWhQty;

    /**
    * 是否是不合格品库产生，0:否，1:是
    */
    @ApiModelProperty(value="是否是不合格品库产生，0:否，1:是")
    private Integer source;

    /**
    * 备注
    */
    @ApiModelProperty(value="备注")
    private String remark;

    /**
    * 创建人
    */
    @ApiModelProperty(value="创建人")
    private Long createdBy;

    /**
    * 创建时间
    */
    @ApiModelProperty(value="创建时间")
    private Date createdTime;

    /**
    * 更新人
    */
    @ApiModelProperty(value="更新人")
    private Long updatedBy;

    /**
    * 更新时间
    */
    @ApiModelProperty(value="更新时间")
    private Date updatedTime;

    /**
    * 冗余字段
    */
    @ApiModelProperty(value="冗余字段")
    private String def1;

    /**
    * 冗余字段
    */
    @ApiModelProperty(value="冗余字段")
    private String def2;

    /**
    * 冗余字段
    */
    @ApiModelProperty(value="冗余字段")
    private String def3;

    /**
    * 冗余字段
    */
    @ApiModelProperty(value="冗余字段")
    private String def4;

    /**
    * 冗余字段
    */
    @ApiModelProperty(value="冗余字段")
    private BigDecimal def5;
}