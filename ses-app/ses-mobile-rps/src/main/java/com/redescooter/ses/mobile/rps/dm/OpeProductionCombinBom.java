package com.redescooter.ses.mobile.rps.dm;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
*@author assert
*@date 2021/1/8 13:48
*/
@Data
public class OpeProductionCombinBom {
    /**
    * 主键id
    */
    private Long id;

    /**
    * 逻辑删除
    */
    private Integer dr;

    /**
    * 租户ID
    */
    private Long tenantId;

    /**
    * 部门id（做数据权限用）
    */
    private Long deptId;

    /**
    * 版本标识
    */
    private String versionIdentificat;

    /**
    * bom编号
    */
    private String bomNo;

    /**
    * 采购周期
    */
    private Integer procurementCycle;

    /**
    * 激活时间
    */
    private Date effectiveDate;

    /**
    * 分组的id
    */
    private Long groupId;

    /**
    * 颜色的id
    */
    private Long colorId;

    /**
    * 是否禁用，0：否，1：是
    */
    private Integer disable;

    /**
    * 状态，1：已激活，2：未激活，:3：已失效，4：已作废
    */
    private Integer bomStatus;

    /**
    * 发布人id
    */
    private Integer announUserId;

    /**
    * 操作发布人id
    */
    private Long opAnnounUserId;

    /**
    * 版本
    */
    private String versoin;

    /**
    * 部件数量
    */
    private Integer partsQty;

    /**
    * 中文名称
    */
    private String cnName;

    /**
    * 名称
    */
    private String enName;

    /**
    * 法文名称
    */
    private String frName;

    /**
    * 是否有质检模板，0：否，1：是
    */
    private Boolean qcFlag;

    /**
    * 创建人
    */
    private Integer createdBy;

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