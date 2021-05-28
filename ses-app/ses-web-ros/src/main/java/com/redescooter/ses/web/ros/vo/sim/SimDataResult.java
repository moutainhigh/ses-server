package com.redescooter.ses.web.ros.vo.sim;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName SimDataResult
 * @Description sim返参
 * @Author Charles
 * @Date 2021/05/27 17:12
 */
@Data
@Builder
@ApiModel("sim返参")
@NoArgsConstructor
@AllArgsConstructor
public class SimDataResult {

    @ApiModelProperty("集合")
    private List list;

    @ApiModelProperty("总条数")
    private int total;
}
