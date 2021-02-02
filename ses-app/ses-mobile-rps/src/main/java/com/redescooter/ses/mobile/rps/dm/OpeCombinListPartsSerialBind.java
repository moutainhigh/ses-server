package com.redescooter.ses.mobile.rps.dm;

import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author assert
 * @date 2021/1/27 20:15
 */
@ApiModel(value = "com-redescooter-ses-mobile-rps-dm-OpeCombinListPartsSerialBind")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OpeCombinListPartsSerialBind {
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 逻辑删除
     */
    @ApiModelProperty(value = "逻辑删除")
    @TableLogic
    private Integer dr;

    /**
     * 组装清单表id
     */
    @ApiModelProperty(value = "组装清单表id")
    private Long orderBId;

    /**
     * 组装产品类型 1车辆 2组装件
     */
    @ApiModelProperty(value = "组装产品类型 1车辆 2组装件")
    private Integer orderType;

    /**
     * 序列号
     */
    @ApiModelProperty(value = "序列号")
    private String serialNum;

    /**
     * 部件本身序列号(厂商序列号)
     */
    @ApiModelProperty(value = "部件本身序列号(厂商序列号)")
    private String defaultSerialNum;

    /**
     * 批次号
     */
    @ApiModelProperty(value = "批次号")
    private String lot;

    /**
     * 蓝牙mac地址
     */
    @ApiModelProperty(value = "蓝牙mac地址")
    private String bluetoothMacAddress;

    /**
     * 产品Id
     */
    @ApiModelProperty(value = "产品Id")
    private Long productId;

    /**
     * 产品类型：3零部件
     */
    @ApiModelProperty(value = "产品类型：3零部件")
    private Integer productType;

    /**
     * 数量
     */
    @ApiModelProperty(value = "数量")
    private Integer qty;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

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

    public static OpeCombinListPartsSerialBindBuilder builder() {
        return new OpeCombinListPartsSerialBindBuilder();
    }
}
