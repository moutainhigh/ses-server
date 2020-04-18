package com.redescooter.ses.mobile.rps.vo.scooterqc;

import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import io.swagger.annotations.ApiModel;
import javafx.scene.shape.Path;
import lombok.*;

import io.swagger.annotations.*;

/**
 * @ClassNameScooterQcIdEnter
 * @Description
 * @Author kyle
 * @Date2020/4/17 20:21
 * @Version V1.0
 **/

@ApiModel(value = "整车部件质检项操作入参", description = "整车部件质检项操作入参")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ScooterQcIdEnter extends PageEnter {

    @ApiModelProperty(value = "组装单子单id")
    private Long scooterBId;

    @ApiModelProperty(value = "产品id")
    private Long partId;

//
//    @ApiModelProperty(value = "质检项选项集合")
//    private String scooterQcItemOptionEnter;


}
