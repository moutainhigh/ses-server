package com.redescooter.ses.api.common.vo.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "公共结果集", description = "公共结果集")
public class GeneralResult implements Serializable {

    @ApiModelProperty(value = "请求id",hidden = true)
    private String requestId;

    public GeneralResult() {
    }

    public GeneralResult(String requestId) {
        this.requestId = requestId;
    }
}
