package com.redescooter.ses.mobile.rps.vo.scooterqc;

import com.redescooter.ses.api.common.vo.base.PageEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @ClassNameScooterQcIdItemEnter
 * @Description
 * @Author kyle
 * @Date2020/4/21 11:10
 * @Version V1.0
 **/
@ApiModel(value = "整车部件质检项操作入参", description = "整车部件质检项操作入参")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ScooterQcIdItemEnter extends PageEnter {

    @ApiModelProperty(value = "组装单子单id")
    private Long id;

}
