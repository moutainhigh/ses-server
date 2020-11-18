package com.redescooter.ses.web.ros.vo.restproduct;

import com.redescooter.ses.api.common.vo.base.PageEnter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author Aleks
 * @Description
 * @Date  2020/10/20 11:42
 * @Param
 * @return
 **/
@Data
public class SaleListEnter extends PageEnter {


    @ApiModelProperty(value = "销售状态，0：不可销售，1：可销售")
    private Integer saleStutas;


    @ApiModelProperty("关键字")
    private String keyword;

}
