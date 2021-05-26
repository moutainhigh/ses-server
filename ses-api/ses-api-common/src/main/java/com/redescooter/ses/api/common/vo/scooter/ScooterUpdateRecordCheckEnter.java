package com.redescooter.ses.api.common.vo.scooter;

import com.redescooter.ses.api.common.annotation.NotEmpty;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description
 * @Author Chris
 * @Date 2021/5/26 22:12
 */
@Data
@ApiModel(value = "校验平板升级更新记录入参")
public class ScooterUpdateRecordCheckEnter extends GeneralEnter {

    @ApiModelProperty(value = "仪表序列号", required = true)
    private String tabletSn;

    @ApiModelProperty(value = "版本号", required = true)
    private String versionCode;

    @ApiModelProperty(value = "表示这次升级推送的唯一性,用uuid", required = true)
    private String updateCode;

}
