package com.redescooter.ses.mobile.rps.vo.scooterqc;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import lombok.*;

import java.util.List;

import io.swagger.annotations.*;

@ApiModel(value = "部件列表质检选项入参", description = "部件列表质检选项入参")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ScooterQcItemOptionsEnter extends GeneralEnter {

    @ApiModelProperty(value = "主键",required = true)
    private Long id;

    @ApiModelProperty(value = "选项排序")
    private Integer Num;

    @ApiModelProperty(value = "用户上传图片URL")
    private String imageUrl;

    @ApiModelProperty(value = "部件具体选项集合")
    private List<ScooterQcItemOptionEnter> scooterQcItemOptionEnterList;
}
