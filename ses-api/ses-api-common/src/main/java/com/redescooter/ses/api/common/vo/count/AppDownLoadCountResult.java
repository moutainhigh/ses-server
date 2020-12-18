package com.redescooter.ses.api.common.vo.count;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName:AppDownLoadCountResult
 * @description: AppDownLoadCountResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/12/17 10:32
 */
@Data
@ApiModel(value = "OMSapp下载量出参")
public class AppDownLoadCountResult extends GeneralResult {

    @ApiModelProperty("app类型，1：IOS，2：Android")
    private Integer type;

    @ApiModelProperty("app下载量")
    private Integer downloadTotal = 0;

    @ApiModelProperty("下载量的百分比")
    private String percentRatio;


}
