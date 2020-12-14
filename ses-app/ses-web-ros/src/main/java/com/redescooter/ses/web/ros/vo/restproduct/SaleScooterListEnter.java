package com.redescooter.ses.web.ros.vo.restproduct;

import com.redescooter.ses.api.common.vo.base.PageEnter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassNameSaleScooterListEnter
 * @Description
 * @Author Aleks
 * @Date2020/10/13 15:24
 * @Version V1.0
 **/
@Data
public class SaleScooterListEnter extends PageEnter {


    @ApiModelProperty(value="车辆所属规格id")
    private Long specificatId;


    @ApiModelProperty(value = "销售状态，0：不可销售，1：可销售")
    private Integer saleStutas;


    @ApiModelProperty("关键字")
    private String keyword;

}
