package com.redescooter.ses.web.ros.vo.supplierChaim;

import java.sql.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.vo.base.PageEnter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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

    @ApiModelProperty(value = "刷新开始时间",required = false)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="UTC")
    private Date refuseStartTime;

    @ApiModelProperty(value = "刷新结束时间",required = false)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="UTC")
    private Date refuseEndTime;

    @ApiModelProperty(value = "关键字",required = false)
    private String keyword;
}
