package com.redescooter.ses.web.ros.vo.restproduct;

import com.baomidou.mybatisplus.annotation.TableField;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassNameSaleScooterSaveOrUpdateEnter
 * @Description
 * @Author Aleks
 * @Date2020/10/13 10:00
 * @Version V1.0
 **/
@Data
public class SaleScooterSaveOrUpdateEnter extends GeneralEnter {

    @ApiModelProperty("id")
    private Long id;

    @TableField(value = "product_name")
    @ApiModelProperty(value="产品名称")
    private String productName;

    @TableField(value = "product_code")
    @ApiModelProperty(value="产品编码")
    private String productCode;

    @TableField(value = "specificat_id")
    @ApiModelProperty(value="车辆所属规格id")
    private Long specificatId;


}
