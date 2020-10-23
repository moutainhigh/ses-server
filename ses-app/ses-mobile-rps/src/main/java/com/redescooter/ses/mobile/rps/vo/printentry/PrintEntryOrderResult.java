package com.redescooter.ses.mobile.rps.vo.printentry;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 *  @author: alex
 *  @Date: 2020/10/23 14:36
 *  @version：V ROS 1.8.3
 *  @Description:
 */
@ApiModel(value = "单据部品列表", description = "单据部品列表")
@Data
public class PrintEntryOrderResult extends GeneralResult {
    @ApiModelProperty(value = "id 子单据Id")
    private Long id;

    @ApiModelProperty(value = "id 父单据Id")
    private Long parentId;

    @ApiModelProperty(value = "部品编码")
    private String partN;

    @ApiModelProperty(value = "部品名称")
    private String partName;

    @ApiModelProperty(value = "idclas")
    private String idclass;

    @ApiModelProperty(value = "数量")
    private int qty;

    @ApiModelProperty(value = "部件质检记录详情")
    List<PrintEntryOrderDetailResult> partQcDetailList;

}
