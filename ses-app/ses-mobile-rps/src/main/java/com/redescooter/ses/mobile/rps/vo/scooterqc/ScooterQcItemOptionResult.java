package com.redescooter.ses.mobile.rps.vo.scooterqc;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@ApiModel(value = "部件列表具体质检选项出参", description = "部件列表具体质检选项出参")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ScooterQcItemOptionResult extends GeneralResult {

    @ApiModelProperty(value = "质检项id")
    private Long id;

    @ApiModelProperty(value = "质检项名称")
    private String qcName;

    @ApiModelProperty(value = "质检项结果")
    private String qcResult;

    @ApiModelProperty(value = "选项排序")
    private Integer optionNum;

    @ApiModelProperty(value = "是否允许上传图片")
    private Boolean uploadFlag;





}
