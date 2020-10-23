package com.redescooter.ses.web.ros.vo.restproductionorder.purchaseorder;

import com.redescooter.ses.api.common.vo.base.PageEnter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassNamePuraseListEnter
 * @Description
 * @Author Aleks
 * @Date2020/10/23 20:00
 * @Version V1.0
 **/
@Data
public class PuraseListEnter extends PageEnter {

    @ApiModelProperty(value = "关键字")
    private String keyword;

    @ApiModelProperty(value = "采购单状态，0：草稿，10：待备货，20：备货中，30：待发货，40：待签收，50：已签收，60：已完成，70：已取消")
    private Integer purchaseStatus;

    @ApiModelProperty("调拨单类型,1:车辆，2:组装件，3:部件")
    private Integer classType;

}
