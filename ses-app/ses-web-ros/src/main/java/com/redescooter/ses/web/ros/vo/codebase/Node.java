package com.redescooter.ses.web.ros.vo.codebase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @ClassName Node
 * @Description 流程节点
 * @Author Charles
 * @Date 2021/05/20 16:38
 */
@Data
@AllArgsConstructor
@ApiModel("流程节点")
public class Node {

    @ApiModelProperty("名称")
    private String nodeName;

    @ApiModelProperty("时间")
    private String nodeTime;
}
