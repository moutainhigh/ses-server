package com.redescooter.ses.web.ros.dm;

import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 部件的草稿表
 */
@ApiModel(value = "com-redescooter-ses-web-ros-dm-OpeProductionPartsDraft")
@Data
public class OpeProductionPartsDraft {
    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    private Long id;

    /**
     * 逻辑删除
     */
    @ApiModelProperty(value = "逻辑删除")
    @TableLogic
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
     * 部件编号
     */
    @ApiModelProperty(value = "部件编号")
    private String partsNo;

    /**
     * 部件类型，1：Parts，2：Accessory，3：Battery，4：Scooter，5：Combination
     */
    @ApiModelProperty(value = "部件类型，1：Parts，2：Accessory，3：Battery，4：Scooter，5：Combination")
    private Integer partsType;

    /**
     * 部件区域编码id
     */
    @ApiModelProperty(value = "部件区域编码id")
    private Long partsSec;

    /**
     * 是否可采购，0：否，1：是
     */
    @ApiModelProperty(value = "是否可采购，0：否，1：是")
    private Integer snClass;

    /**
     * 是否有序列号，0：否，1：是
     */
    @ApiModelProperty(value = "是否有序列号，0：否，1：是")
    private Integer idCalss;

    /**
     * 是否是组装件，0：否，1：是
     */
    @ApiModelProperty(value = "是否是组装件，0：否，1：是")
    private Integer partsIsAssembly;

    /**
     * 是否可用于组装件，0：否，1：是
     */
    @ApiModelProperty(value = "是否可用于组装件，0：否，1：是")
    private Integer partsIsForAssembly;

    /**
     * 部品数量
     */
    @ApiModelProperty(value = "部品数量")
    private Integer partsQty;

    /**
     * 供应商id
     */
    @ApiModelProperty(value = "供应商id")
    private Long supplierId;

    /**
     * 采购周期
     */
    @ApiModelProperty(value = "采购周期")
    private Integer procurementCycle;

    /**
     * 是否信息完善（只有信息完善了才能发布）
     */
    @ApiModelProperty(value = "是否信息完善（只有信息完善了才能发布）")
    private Boolean perfectFlag;

    /**
     * 图纸
     */
    @ApiModelProperty(value = "图纸")
    private String dwg;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 中文名称
     */
    @ApiModelProperty(value = "中文名称")
    private String cnName;

    /**
     * 英文名称
     */
    @ApiModelProperty(value = "英文名称")
    private String enName;

    /**
     * 法文名称
     */
    @ApiModelProperty(value = "法文名称")
    private String frName;

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
}