package com.redescooter.ses.mobile.rps.dm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.Data;

/**
 *@author assert
 *@date 2021/1/14 15:45
 */
@ApiModel(value="com-redescooter-ses-mobile-rps-dm-OpeWmsStockRecord")
@Data
public class OpeWmsStockRecord {
    /**
    * 主键
    */
    @ApiModelProperty(value="主键")
    private Long id;

    /**
    * 逻辑删除
    */
    @ApiModelProperty(value="逻辑删除")
    private Integer dr;

    /**
    * 关联的库存表ID
    */
    @ApiModelProperty(value="关联的库存表ID")
    private Long relationId;

    /**
    * 关联的单据类型，1:成品库车辆库存单，2:成品库组装件库存单，3:原料库部件库存单，4:不合格品库车辆库存单，5:不合格品库组装件库存单，6:不合格品库部件库存单
    */
    @ApiModelProperty(value="关联的单据类型，1:成品库车辆库存单，2:成品库组装件库存单，3:原料库部件库存单，4:不合格品库车辆库存单，5:不合格品库组装件库存单，6:不合格品库部件库存单")
    private Integer relationType;

    /**
    * 入库类型，1：生产入库，2：返修入库，3：采购入库，5：退料入库，6：其他
    */
    @ApiModelProperty(value="入库类型，1：生产入库，2：返修入库，3：采购入库，5：退料入库，6：其他")
    private Integer inWhType;

    /**
    * 入库数量
    */
    @ApiModelProperty(value="入库数量")
    private Integer inWhQty;

    /**
    * 记录类型，1:入库，2:出库
    */
    @ApiModelProperty(value="记录类型，1:入库，2:出库")
    private Integer recordType;

    /**
    * 仓库类型，1:中国仓库，2:法国仓库
    */
    @ApiModelProperty(value="仓库类型，1:中国仓库，2:法国仓库")
    private Integer stockType;

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
    private Double def5;
}