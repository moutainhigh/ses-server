package com.redescooter.ses.api.mobile.b.vo;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName:CompleteResult
 * @description: CompleteResult
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/29 15:26
 */
@ApiModel(value = "完成出参", description = "完成出参")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = true)
public class CompleteResult extends GeneralResult {

    @ApiModelProperty(value = "公里数 单位 米")
    private String mileage="0";

    @ApiModelProperty(value = "平均速度 单位 m/s")
    private String avg="0";

    @ApiModelProperty(value = " 节省co2 单位 g")
    private String co2="0";

    @ApiModelProperty(value = "节省钱 单位 欧")
    private String money="0";

}
