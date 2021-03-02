package com.redescooter.ses.web.ros.dm;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author chris
 * @since 2021-01-25
 */
@ApiModel(value = "入库单产品序列号绑定表")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Accessors(chain = true)
@JsonInclude(value = Include.NON_NULL)
public class OpeInWhouseOrderSerialBind {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.UUID)
    private Long id;

    @ApiModelProperty(value = "逻辑删除")
    private Integer dr;

    @ApiModelProperty(value = "入库单产品表id")
    private Long orderBId;

    @ApiModelProperty(value = "入库单产品类型 1车辆 2组装件 3部件")
    private Integer orderType;

    @ApiModelProperty(value = "序列号")
    private String serialNum;

    @ApiModelProperty(value = "部件本身序列号(厂商序列号)")
    private String defaultSerialNum;

    @ApiModelProperty(value = "蓝牙mac地址")
    private String bluetoothMacAddress;

    @ApiModelProperty(value = "批次号")
    private String lot;

    @ApiModelProperty(value = "产品Id")
    private Long productId;

    @ApiModelProperty(value = "产品类型：1、整车 2、组合 3、零部件")
    private Integer productType;

    @ApiModelProperty(value = "数量")
    private Integer qty;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "创建人")
    private Long createdBy;

    @ApiModelProperty(value = "创建时间", hidden = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdTime;

    @ApiModelProperty(value = "更新人")
    private Long updatedBy;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedTime;

    @ApiModelProperty(value = "冗余字段")
    private String def1;

    @ApiModelProperty(value = "冗余字段")
    private String def2;

    @ApiModelProperty(value = "冗余字段")
    private String def3;

    @ApiModelProperty(value = "冗余字段")
    private String def4;

    @ApiModelProperty(value = "冗余字段")
    private BigDecimal def5;

}