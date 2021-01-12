package com.redescooter.ses.web.ros.vo.restproductionorder.inwhouse;

import com.redescooter.ses.api.common.vo.base.PageEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassNameInWhouseListEnter
 * @Description
 * @Author Aleks
 * @Date2020/11/11 10:48
 * @Version V1.0
 **/
@Data
@ApiModel(value = "入库单的列表传参",description = "入库单的列表传参")
public class InWhouseListEnter extends PageEnter {

    @ApiModelProperty("入库单状态，1： 1： 草稿，:10：待质检，20：质检中，25：待入库，30：已入库")
    private Integer inWhStatus;

    @ApiModelProperty("入库类型，1：生产入库，2：返修入库，3：采购入库，4：退料入库，5：其他")
    private Integer inWhType;

    @ApiModelProperty("关键字")
    private String keyword;

    @ApiModelProperty("入库单类型,1:车辆，2:组装件，3:部件")
    private Integer classType;

    @ApiModelProperty(value = "国家类型，1:中国，2:法国")
    private Integer countryType;

    @ApiModelProperty(value = "是否是不合格品库产生，0:否，1:是")
    private Integer source;

}
