package com.redescooter.ses.mobile.rps.vo.scooterqc;

import com.redescooter.ses.api.common.vo.base.PageEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @ClassNameScooterQcIdEnter
 * @Description
 * @Author kyle
 * @Date2020/4/17 20:21
 * @Version V1.0
 **/

@ApiModel(value = "整车部件质查询入参", description = "整车部件质查询入参")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ScooterQcIdEnter extends PageEnter {

    @ApiModelProperty(value = "组装单id")
    private Long id;

}
