package com.redescooter.ses.api.common.vo.workorder;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.PageResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @ClassNameWorkOrderListVo
 * @Description
 * @Author Aleks
 * @Date2020/12/4 13:55
 * @Version V1.0
 **/
@Data
@ApiModel(value = "工单列表接口的返参")
public class WorkOrderListResult extends GeneralResult {

    @ApiModelProperty(value="ID")
    private Long id;

    /**
     * 工单编号
     */
    @ApiModelProperty(value="工单编号")
    private String orderNo;

    /**
     * 工单来源 1：APP，2：ROS，3：SAAS，4：OMS，5：RPS
     */
    @ApiModelProperty(value="工单来源 1：APP，2：ROS，3：SAAS，4：OMS，5：RPS")
    private Integer source;

    /**
     * 工单状态 1：新建（Pending），2：处理中（In Progress），3：已完成（Completed），4：关闭（Closed）
     */
    @ApiModelProperty(value="工单状态 1：新建（Pending），2：处理中（In Progress），3：已完成（Completed），4：关闭（Closed）")
    private Integer workOrderStatus;

    /**
     * 工单标题
     */
    @ApiModelProperty(value="工单标题")
    private String title;

    /**
     * 联系的邮箱
     */
    @ApiModelProperty(value="联系的邮箱")
    private String contactEmail;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private Date createdTime;




}
