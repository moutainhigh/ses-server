package com.redescooter.ses.web.ros.vo.assign.tobe.enter;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Description
 * @Author Chris
 * @Date 2021/5/18 13:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class ToBeAssignInputBatteryEnter extends GeneralEnter {

    @ApiModelProperty(value = "id:主键,battery:电池,多个时逗号分隔", required = true)
    private String list;

    @ApiModelProperty(value = "客户id", required = true)
    private Long customerId;

}
