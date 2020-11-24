package com.redescooter.ses.web.ros.vo.restproductionorder.assembly;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassNameRosProductionExport
 * @Description
 * @Author Aleks
 * @Date2020/9/30 13:18
 * @Version V1.0
 **/
@Data
public class ProductionAssemblyExport {

    @ApiModelProperty(value = "组装单号")
    @Excel(name = "组装单号",width = 20)
    private String orderNo;

    @ApiModelProperty(value = "产品编号")
    @Excel(name = "产品编号",width = 20)
    private String productN;

    @ApiModelProperty(value = "产品名称")
    @Excel(name = "产品名称",width = 20)
    private String productName;

    @ApiModelProperty(value = "产品类型")
    @Excel(name = "产品类型",width = 20)
    private String productType;

    @ApiModelProperty(value = "数量")
    @Excel(name = "数量",width = 20)
    private Integer qty;

    @ApiModelProperty(value = "颜色名称")
    @Excel(name = "颜色名称",width = 20)
    private String colorName;

    @ApiModelProperty(value = "颜色色值")
    @Excel(name = "颜色色值",width = 20)
    private String colorValue;

    @ApiModelProperty(value = "分组名称")
    @Excel(name = "分组名称",width = 20)
    private String groupName;

}
