package com.redescooter.ses.web.delivery.vo.edscooter;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @ClassName:MobileListResult
 * @description: MobileListResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/13 18:50
 */
@ApiModel(value = "车辆环保数据", description = "车辆环保数据")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class EdScooterGreenDataResult extends GeneralResult {
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "距离下次维修公里数")
    private String nextMaintenanceKm = "0";

    @ApiModelProperty(value = "车牌号")
    private String licensePlate;

    @ApiModelProperty(value = "电量")
    private Integer battery = 0;

    @ApiModelProperty(value = "公里数")
    private String mileage = "0";

    @ApiModelProperty(value = "节省的co2")
    private String co2 = "0";

    @ApiModelProperty(value = "节省的钱")
    private String money = "0";

}
