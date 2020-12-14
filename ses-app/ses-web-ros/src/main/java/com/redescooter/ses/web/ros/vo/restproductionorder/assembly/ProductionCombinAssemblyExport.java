package com.redescooter.ses.web.ros.vo.restproductionorder.assembly;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * @ClassNameRosProductionExport
 * @Description
 * @Author Aleks
 * @Date2020/9/30 13:18
 * @Version V1.0
 **/
@Data
public class ProductionCombinAssemblyExport {

    @Excel(name = "组装单号",width = 20)
    private String orderNo;

    @Excel(name = "数量",width = 20)
    private Integer qty;

    @Excel(name = "产品类型",width = 20)
    private String productType;

    @Excel(name = "产品名称",width = 20)
    private String productName;

    @Excel(name = "产品编号",width = 20)
    private String productN;





}
