package com.redescooter.ses.web.ros.vo.account;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName:RenewAccontEnter
 * @description: RenewAccontEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/24 09:57
 */
@ApiModel(value = "账户续期", description = "账户续期")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
public class RenewAccountEnter extends GeneralEnter {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "开始激活时间")
    private String startRenewAccountTime;

    @ApiModelProperty(value = "结束激活时间")
    private String endRenewAccountTime;
}
