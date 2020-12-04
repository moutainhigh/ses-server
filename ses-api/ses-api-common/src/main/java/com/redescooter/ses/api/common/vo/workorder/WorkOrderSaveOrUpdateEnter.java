package com.redescooter.ses.api.common.vo.workorder;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassNameWorkOrderSaveDTO
 * @Description
 * @Author Aleks
 * @Date2020/12/4 14:51
 * @Version V1.0
 **/
@Data
public class WorkOrderSaveOrUpdateEnter extends GeneralEnter {

    @ApiModelProperty(value="ID")
    private Long id;

    @ApiModelProperty(value="工单标题")
    private String title;

    @ApiModelProperty(value="工单来源 1：APP，2：ROS，3：SAAS，4：OMS，5：RPS")
    private Integer source;

    /**
     * 联系的邮箱
     */
    @ApiModelProperty(value="联系的邮箱")
    private String contactEmail;

    /**
     * 备注
     */
    @ApiModelProperty(value="备注内容")
    private String remark;

    /**
     * 附件1
     */
    @ApiModelProperty(value="图片1")
    private String annexPicture1;

    /**
     * 附件2
     */
    @ApiModelProperty(value="图片2")
    private String annexPicture2;

    /**
     * 附件3
     */
    @ApiModelProperty(value="图片3")
    private String annexPicture3;

}
