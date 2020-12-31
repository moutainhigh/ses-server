package com.redescooter.ses.api.common.vo.workorder;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassNameWorkOrderLogResult
 * @Description
 * @Author Aleks
 * @Date2020/12/4 16:50
 * @Version V1.0
 **/
@Data
@ApiModel(value = "工单记录")
public class WorkOrderLogResult implements Serializable {

    @ApiModelProperty("联系人")
    private String createdByName;

    @ApiModelProperty("消息类型 1：留言， 2：回复")
    private Integer messageType;

    @ApiModelProperty("内容")
    private String remark;

    @ApiModelProperty("图片地址")
    private String url;

    @ApiModelProperty(value="创建时间")
    private Date createdTime;

}
