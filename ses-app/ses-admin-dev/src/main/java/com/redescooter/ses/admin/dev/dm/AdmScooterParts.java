package com.redescooter.ses.admin.dev.dm;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * @author assert
 * @date 2020/12/10 22:43
 */
@Data
public class AdmScooterParts {
    public static final String COL_ID = "id";
    public static final String COL_DR = "dr";
    public static final String COL_SCOOTER_ID = "scooter_id";
    public static final String COL_NAME = "name";
    public static final String COL_R_SN = "r_sn";
    public static final String COL_QTY = "qty";
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
     * ID
     */
    private Long id;

    /**
     * 逻辑删除标识 0正常 1删除
     */
    private Integer dr;

    /**
     * 车辆的id
     */
    private Long scooterId;

    /**
     * 名称
     */
    private String name;

    /**
     * 序列号
     */
    private String sn;

    /**
     * 数量
     */
    private Integer qty;

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
    private String def5;

    /**
     * 冗余字段
     */
    private BigDecimal def6;
}