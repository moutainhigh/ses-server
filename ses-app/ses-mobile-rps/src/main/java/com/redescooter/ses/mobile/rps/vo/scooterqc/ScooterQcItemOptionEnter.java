package com.redescooter.ses.mobile.rps.vo.scooterqc;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.apache.poi.ss.formula.functions.T;

import java.io.Serializable;


@ApiModel(value = "部件列表具体质检选项入参", description = "部件列表具体质检选项入参")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ScooterQcItemOptionEnter extends GeneralEnter {

    @ApiModelProperty(value = "质检项id")
    private Long qcId;

    @ApiModelProperty(value = "用户上传图片URL")
    private String imageUrl;

    @ApiModelProperty(value = "质检项结果id")
    private Long qcResultId;



}
