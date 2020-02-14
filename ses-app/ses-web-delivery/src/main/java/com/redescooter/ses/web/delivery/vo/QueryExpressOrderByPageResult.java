package com.redescooter.ses.web.delivery.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;

/**
 * @ClassName:QueryExpressDeliveryByPageResult
 * @description: QueryExpressDeliveryByPageResult
 * @author: Alex
 * @Version：1.3
 * @create: 2019/09/12 15:44
 */
@ApiModel(value = "Order分页结果集", description = "Order结果集")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class QueryExpressOrderByPageResult extends GeneralResult {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "订单编号")
    private String orderNo;

    @ApiModelProperty(value = "批次号")
    private String batchNo;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "城市")
    private String recipientCity;

    @ApiModelProperty(value = "区域")
    private String recipientPostcode;

    @ApiModelProperty(value = "收件人姓名")
    private String recipientName;

    @ApiModelProperty(value = "收件人邮箱")
    private String recipientMail;

    @ApiModelProperty(value = "收件人地址")
    private String recipientAddress;

    @ApiModelProperty(value = "完成时间")
    private Date deliveredTime;

    @ApiModelProperty(value = "创建时间")
    private Date createdTime;

    @ApiModelProperty(value = "司机名字")
    private String driverFirstName;

    @ApiModelProperty(value = "司机名字")
    private String driverLastName;

    @ApiModelProperty(value="车牌号")
    private String licensePlate;
}
