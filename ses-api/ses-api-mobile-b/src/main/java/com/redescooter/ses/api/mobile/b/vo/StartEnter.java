package com.redescooter.ses.api.mobile.b.vo;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.mobile.b.exception.ValidationExceptionCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName:StartEnter
 * @description: StartEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/29 15:55
 */
@ApiModel(value = "开始订单入参", description = "开始订单入参")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class StartEnter extends GeneralEnter {

    @ApiModelProperty(value = "主键")
    @NotNull(code = ValidationExceptionCode.ID_IS_EMPTY,message = "Id 为空")
    private Long id;

    @ApiModelProperty(value = "是否启动蓝牙模式")
    @NotNull
    private Boolean bluetoothCommunication = Boolean.FALSE;

    @ApiModelProperty(value = "经度")
    private String lat;

    @ApiModelProperty(value = "纬度")
    private String lng;

    @ApiModelProperty(value = "距离 单位 米 ")
    @NotNull(code = ValidationExceptionCode.MILEAGE_IS_EMPTY, message = "距离为空")
    //@Regexp(value= RegexpConstant.number,code = ValidationExceptionCode.DATA_IS_ILLEGLE,message = "数据非法")
    private String mileage;
}
