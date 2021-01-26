package com.redescooter.ses.web.ros.vo.wms.cn;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description
 * @Author Chris
 * @Date 2021/1/26 9:50
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ApiModel(value = "详情出参", description = "详情出参")
public class WmsDetailResult extends GeneralResult implements Serializable {

    private static final long serialVersionUID = 1869465314089358445L;

    @ApiModelProperty(value = "序列号")
    private String rsn;

    @ApiModelProperty(value = "库存状态 1可继续使用 2已被别人使用")
    private Integer stockStatus;

    @ApiModelProperty(value = "批次号")
    private String lotNum;

    @ApiModelProperty(value = "供应商序列号")
    private String sn;

    @ApiModelProperty(value = "蓝牙mac地址")
    private String bluetoothMacAddress;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "创建人")
    private Long createdBy;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdTime;

    @ApiModelProperty(value = "更新人")
    private Long updatedBy;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedTime;

}
