package com.redescooter.ses.mobile.rps.dm;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
*@author assert
*@date 2020/12/30 15:26
*/
@Data
public class OpeOrderSerialBind {
    /**
    * 主键
    */
    private Long id;

    /**
    * 逻辑删除
    */
    private Integer dr;

    /**
    * 单据类型 1调拨点 2、采购单 3、发货单 4出库单 5委托单
    */
    private Integer orderType;

    /**
    * 子单据Id
    */
    private Long orderBId;

    /**
    * 序列号
    */
    private String serialNum;

    /**
    * 批次号
    */
    private String lot;

    /**
    * 产品Id
    */
    private Long productId;

    /**
    * 产品类型：1、整车 2、组合 3、零部件
    */
    private Integer productType;

    /**
    * 数量
    */
    private Integer qty;

    /**
    * 备注
    */
    private String remark;

    /**
    * 创建人
    */
    private Long createdBy;

    /**
    * 创建时间
    */
    private Date createdTime;

    /**
    * 更新人
    */
    private Long updatedBy;

    /**
    * 更新时间
    */
    private Date updatedTime;

    /**
    * 冗余字段
    */
    private String def1;

    /**
    * 冗余字段
    */
    private String def2;

    /**
    * 冗余字段
    */
    private String def3;

    /**
    * 冗余字段
    */
    private String def4;

    /**
    * 冗余字段
    */
    private BigDecimal def5;
}