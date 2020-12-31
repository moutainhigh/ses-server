package com.redescooter.ses.api.common.vo.workorder;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @ClassNameWorkOrderDetailResult
 * @Description
 * @Author Aleks
 * @Date2020/12/4 16:28
 * @Version V1.0
 **/
@Data
@ApiModel(value = "工单的详情返参")
public class WorkOrderDetailResult extends GeneralResult {

    @ApiModelProperty(value="ID")
    private Long id;

    @ApiModelProperty(value="工单编号")
    private String orderNo;

    @ApiModelProperty(value="工单状态 1：新建（Pending），2：处理中（In Progress），3：已完成（Completed），4：关闭（Closed）")
    private Integer workOrderStatus;

    @ApiModelProperty(value="工单标题")
    private String title;

    @ApiModelProperty(value="备注")
    private String remark;

    @ApiModelProperty(value="联系的邮箱")
    private String contactEmail;

    @ApiModelProperty(value="图片1")
    private String annexPicture1;

    @ApiModelProperty(value="图片2")
    private String annexPicture2;

    @ApiModelProperty(value="图片3")
    private String annexPicture3;

    @ApiModelProperty("创建人名称")
    private String createdByName;

    @ApiModelProperty("消息记录")
    private List<WorkOrderLogResult> logs;



}
