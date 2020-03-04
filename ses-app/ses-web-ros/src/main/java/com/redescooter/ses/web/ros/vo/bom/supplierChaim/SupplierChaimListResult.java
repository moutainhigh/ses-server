package com.redescooter.ses.web.ros.vo.bom.supplierChaim;

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

import java.sql.Date;

/**
 * @ClassName:SupplierChaimListResult
 * @description: SupplierChaimListResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/02/25 14:47
 */
@ApiModel(value = "供应链列表", description = "供应链列表")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class SupplierChaimListResult extends GeneralResult {
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "产品编码")
    private String productN;

    @ApiModelProperty(value = "产品名字")
    private String productEnName;

    @ApiModelProperty(value = "产品名字")
    private String productCnName;

    @ApiModelProperty(value = "产品名字")
    private String productFrName;

    @ApiModelProperty(value = "类型")
    private String type;

    @ApiModelProperty(value = "价格")
    private String productPrice;

    @ApiModelProperty(value = "货币单位")
    private String unit;

    @ApiModelProperty(value = "刷新时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date refuseTime;
}
