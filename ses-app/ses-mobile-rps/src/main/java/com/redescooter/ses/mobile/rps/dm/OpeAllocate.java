package com.redescooter.ses.mobile.rps.dm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 调拨单
 */
@ApiModel(value = "com-redescooter-ses-mobile-rps-dm-OpeAllocate")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OpeAllocate implements Serializable {
    public static final String COL_ID = "id";
    public static final String COL_DR = "dr";
    public static final String COL_TENANT_ID = "tenant_id";
    public static final String COL_USER_ID = "user_id";
    public static final String COL_ALLOCATE_NUM = "allocate_num";
    public static final String COL_STATUS = "status";
    public static final String COL_COUNT = "count";
    public static final String COL_CONSIGNEE_ID = "consignee_id";
    public static final String COL_PREPARATION_WAIT_TOTAL = "preparation_wait_total";
    public static final String COL_REVISION = "revision";
    public static final String COL_CREATED_BY = "created_by";
    public static final String COL_CREATED_TIME = "created_time";
    public static final String COL_UPDATED_BY = "updated_by";
    public static final String COL_UPDATED_TIME = "updated_time";
    public static final String COL_DEF1 = "def1";
    public static final String COL_DEF2 = "def2";
    public static final String COL_DEF3 = "def3";
    public static final String COL_DEF5 = "def5";
    public static final String COL_DEF6 = "def6";
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 删除标识
     */
    @ApiModelProperty(value = "删除标识")
    private Integer dr;

    /**
     * 租户id
     */
    @ApiModelProperty(value = "租户id")
    private Long tenantId;

    /**
     * userid
     */
    @ApiModelProperty(value = "userid")
    private Long userId;

    /**
     * 调拨编号
     */
    @ApiModelProperty(value = "调拨编号")
    private String allocateNum;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private String status;

    /**
     * 数量
     */
    @ApiModelProperty(value = "数量")
    private Integer count;

    /**
     * 收获人Id
     */
    @ApiModelProperty(value = "收获人Id")
    private Long consigneeId;

    /**
     * 待备料总数
     */
    @ApiModelProperty(value = "待备料总数")
    private Integer preparationWaitTotal;

    /**
     * 乐观锁
     */
    @ApiModelProperty(value = "乐观锁")
    private Integer revision;

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
    private String def5;

    /**
     * 冗余字段
     */
    @ApiModelProperty(value = "冗余字段")
    private Double def6;

    private static final long serialVersionUID = 1L;

    public static OpeAllocateBuilder builder() {
        return new OpeAllocateBuilder();
    }
}