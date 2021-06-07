package com.redescooter.ses.wh.fr.dm;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Charles
 * @since 2021-05-26 20:58:05
 */
@Data
@ApiModel("sim卡信息")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "ope_sim_information")
public class OpeSimInformation extends Model<OpeSimInformation> implements Serializable {
    private static final long serialVersionUID = 759574758915851088L;

    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty("主键")
    private Long id;

    @TableLogic
    @TableField(value = "dr")
    @ApiModelProperty("逻辑删除标识 0正常 1删除")
    private Integer dr;

    @TableField(value = "sim_iccid")
    @ApiModelProperty("sim卡的id")
    private String simIccid;

    @TableField(value = "rsn")
    @ApiModelProperty("rsn码")
    private String rsn;

    @TableField(value = "bluetooth_mac_address")
    @ApiModelProperty("蓝牙地址")
    private String bluetoothMacAddress;

    @TableField(value = "tablet_sn")
    @ApiModelProperty("仪表")
    private String tabletSn;

    @TableField(value = "vin")
    @ApiModelProperty("vin码")
    private String vin;

    @TableField(value = "created_by")
    @ApiModelProperty("创建人")
    private Long createdBy;

    @TableField(value = "created_time")
    @ApiModelProperty("创建时间")
    private Date createdTime;

    @TableField(value = "updated_by")
    @ApiModelProperty("修改人")
    private Long updatedBy;

    @TableField(value = "updated_time")
    @ApiModelProperty("修改时间")
    private Date updatedTime;

    @TableField(value = "def1")
    @ApiModelProperty("冗余字段")
    private String def1;

    @TableField(value = "def2")
    @ApiModelProperty("冗余字段")
    private String def2;

    @TableField(value = "def3")
    @ApiModelProperty("冗余字段")
    private String def3;

    @TableField(value = "def5")
    @ApiModelProperty("冗余字段")
    private String def5;

    @TableField(value = "def6")
    @ApiModelProperty("冗余字段")
    private Double def6;

    public static final String COL_ID = "id";

    public static final String COL_DR = "dr";

    public static final String COL_SIM_ICCID = "sim_iccid";

    public static final String COL_RSN = "rsn";

    public static final String COL_BLUETOOTH_MAC_ADDRESS = "bluetooth_mac_address";

    public static final String COL_TABLET_SN = "tablet_sn";

    public static final String COL_VIN = "vin";

    public static final String COL_CREATED_BY = "created_by";

    public static final String COL_CREATED_TIME = "created_time";

    public static final String COL_UPDATED_BY = "updated_by";

    public static final String COL_UPDATED_TIME = "updated_time";

    public static final String COL_DEF1 = "def1";

    public static final String COL_DEF2 = "def2";

    public static final String COL_DEF3 = "def3";

    public static final String COL_DEF5 = "def5";

    public static final String COL_DEF6 = "def6";

}
