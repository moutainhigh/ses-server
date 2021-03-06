package com.redescooter.ses.web.ros.vo.bom.supplierChaim;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.constant.DateConstant;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;



/**
 * @ClassName:SupplierChaimListEnter
 * @description: SupplierChaimListEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/02/25 14:43
 */
@ApiModel(value = "供应链列表入参", description = "供应链列表入参")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class SupplierChaimListEnter extends PageEnter {

    @ApiModelProperty(value = "部品类型，all 不传")
    private String partType;

    @ApiModelProperty(value = "刷新开始时间")
    @DateTimeFormat(pattern = DateConstant.DEFAULT_DATETIME_FORMAT)
    @JsonFormat(pattern = DateConstant.DEFAULT_DATETIME_FORMAT, timezone = DateConstant.UTC)
    private Date refuseStartTime;

    @ApiModelProperty(value = "刷新结束时间")
    @DateTimeFormat(pattern = DateConstant.DEFAULT_DATETIME_FORMAT)
    @JsonFormat(pattern = DateConstant.DEFAULT_DATETIME_FORMAT, timezone = DateConstant.UTC)
    private Date refuseEndTime;

    @ApiModelProperty(value = "关键字")
    private String keyword;
}
