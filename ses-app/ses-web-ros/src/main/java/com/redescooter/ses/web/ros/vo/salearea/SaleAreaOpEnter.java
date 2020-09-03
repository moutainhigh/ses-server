package com.redescooter.ses.web.ros.vo.salearea;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassNameSaleAreaOpEnter
 * @Description
 * @Author Aleks
 * @Date2020/9/3 15:02
 * @Version V1.0
 **/
@Data
public class SaleAreaOpEnter extends GeneralEnter {

    @ApiModelProperty("id")
    private Long id;

}
