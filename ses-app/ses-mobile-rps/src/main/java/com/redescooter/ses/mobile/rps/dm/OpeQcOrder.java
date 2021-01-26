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
 *@author assert
 *@date 2021/1/26 10:56
 */
@ApiModel(value="com-redescooter-ses-mobile-rps-dm-OpeQcOrder")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OpeQcOrder {
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
    private Long tenantId;

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
    * 质检单号
    */
    @ApiModelProperty(value="质检单号")
    private String qcNo;

    /**
    * 质检状态 1待质检 10质检中 20质检完成
    */
    @ApiModelProperty(value="质检状态 1待质检 10质检中 20质检完成")
    private Integer qcStatus;

    /**
    * 质检单据类型,1:车辆，2:组装件，3:部件
    */
    @ApiModelProperty(value="质检单据类型,1:车辆，2:组装件，3:部件")
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
    * 关联的单据类型 4出库单 7生产采购单 9组装单
    */
    @ApiModelProperty(value="关联的单据类型 4出库单 7生产采购单 9组装单")
    private Integer relationOrderType;

    /**
    * 质检类型 1采购 2退料 3生产 4发货 5返修 6其他
    */
    @ApiModelProperty(value="质检类型 1采购 2退料 3生产 4发货 5返修 6其他")
    private Integer qcType;

    /**
    * 质检总数量
    */
    @ApiModelProperty(value="质检总数量")
    private Integer qcQty;

    /**
    * 备注
    */
    @ApiModelProperty(value="备注")
    private String remark;

    /**
    * 质检完成时间
    */
    @ApiModelProperty(value="质检完成时间")
    private Date qcCompletionTime;

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