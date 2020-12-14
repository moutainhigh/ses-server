package com.redescooter.ses.web.ros.vo.restproduct.production;

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
public class RosProductionExport {

    @Excel(name = "NANE",width = 20)
    private String name;

    @Excel(name = "BOM N°",width = 20)
    private String productN;

    @Excel(name = "VERSION",width = 20)
    private String version;

    @Excel(name = "PARTS N°",width = 20)
    private String partsNo;

    @Excel(name = "SEC",width = 20)
    private String sec;

    @Excel(name = "EN NAME",width = 20)
    private String enName;

    @Excel(name = "CN NAME",width = 20)
    private String cnName;

    @Excel(name = "LEAD TIME",width = 20)
    private Integer procurementCycle;

    @Excel(name = "QTY",width = 20)
    private Integer qty;


}
