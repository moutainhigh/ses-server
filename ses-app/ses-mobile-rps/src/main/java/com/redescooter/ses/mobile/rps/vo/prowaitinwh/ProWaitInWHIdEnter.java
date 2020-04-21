package com.redescooter.ses.mobile.rps.vo.prowaitinwh;

import com.redescooter.ses.api.common.vo.base.PageEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @ClassNameProWaitInWHIdEnter
 * @Description
 * @Author kyle
 * @Date2020/4/18 10:27
 * @Version V1.0
 **/
@ApiModel(value = "整车部件入库项操作入参", description = "整车部件入库项操作入参")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ProWaitInWHIdEnter extends PageEnter {

    @ApiModelProperty(value = "组装单子单id")
    private Long scooterBId;

    @ApiModelProperty(value = "产品id")
    private Long partId;



}
