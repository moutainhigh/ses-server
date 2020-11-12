package com.redescooter.ses.web.ros.vo.restproductionorder.inwhouse;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @ClassNameInWhouseListEnter
 * @Description
 * @Author Aleks
 * @Date2020/11/11 10:48
 * @Version V1.0
 **/
@Data
@ApiModel(value = "入库单的列表返参对象",description = "入库单的列表返参对象")
public class InWhouseListResult extends GeneralResult {


    @ApiModelProperty(value="主键id")
    private Long id;

    @ApiModelProperty(value="入库单号")
    private String inWhNo;

    @ApiModelProperty(value="入库单状态， 1： 草稿，:10：待质检，20：质检中，30：已入库")
    private Integer inWhStatus;

    @ApiModelProperty(value="入库数量")
    private Integer inWhQty;

    @ApiModelProperty(value="入库类型，1：生产入库，2：返修入库，3：采购入库，4：退料入库，5：其他")
    private Integer inWhType;

    @ApiModelProperty(value = "创建人名称")
    private String createdByName;

    @ApiModelProperty(value = "创建时间")
    private Date createdTime;

}
