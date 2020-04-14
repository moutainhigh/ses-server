package com.redescooter.ses.mobile.rps.vo.scooterqc;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@ApiModel(value = "部件列表质检选项出参", description = "部件列表质检选项出参")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ScooterQcItemOptionsResult extends GeneralResult {

    @ApiModelProperty(value = "主键",required = true)
    private Long id;

    @ApiModelProperty(value = "选项排序")
    private Integer Num;

    @ApiModelProperty(value = "组装单id")
    private Long scooterId;

    @ApiModelProperty(value = "部件id")
    private Long partId;

    @ApiModelProperty(value = "用户上传图片URL")
    private String imageUrl;

    @ApiModelProperty(value = "部件具体选项集合")
    private List<ScooterQcItemOptionResult> scooterQcItemOptionResults;
}
