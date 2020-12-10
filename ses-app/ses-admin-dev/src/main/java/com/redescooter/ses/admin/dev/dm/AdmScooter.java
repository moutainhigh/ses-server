package com.redescooter.ses.admin.dev.dm;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * @author assert
 * @date 2020/12/10 22:42
 */
@Data
public class AdmScooter {
    public static final String COL_ID = "id";
    public static final String COL_DR = "dr";
    public static final String COL_R_SN = "r_sn";
    public static final String COL_GROUP_ID = "group_id";
    public static final String COL_COLOR_ID = "color_id";
    public static final String COL_MAC_ADDRESS = "mac_address";
    public static final String COL_SCOOTER_CONTROLLER = "scooter_controller";
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
    /**
     * ID
     */
    private Long id;

    /**
     * 逻辑删除标识 0正常 1删除
     */
    private Integer dr;

    /**
     * 序列号
     */
    private String sn;

    /**
     * 分组id（车型）
     */
    private Long groupId;

    /**
     * 分组名称
     */
    private String groupName;

    /**
     * 颜色id
     */
    private Long colorId;

    /**
     * 颜色名称
     */
    private String colorName;

    /**
     * 色值
     */
    private String colorValue;

    /**
     * mac地址
     */
    private String macAddress;

    /**
     * 蓝牙名称
     */
    private String macName;

    /**
     * 车辆的配置控制器，1：E50，2：E100，3：E125
     */
    private Integer scooterController;

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