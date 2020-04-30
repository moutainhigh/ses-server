package com.redescooter.ses.mobile.rps.vo.scooterqc;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

/**
 * @ClassNameScooterQcItemListResult
 * @Description
 * @Author kyle
 * @Date2020/4/28 19:20
 * @Version V1.0
 **/
@ApiModel(value = "整车质检具体选项集合出参", description = "整车质检具体选项集合出参")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ScooterQcItemListResult extends GeneralResult {

    @ApiModelProperty(value = "质检项集合")
    private List<ScooterQcItemResult> scooterQcItemResultList;

}
