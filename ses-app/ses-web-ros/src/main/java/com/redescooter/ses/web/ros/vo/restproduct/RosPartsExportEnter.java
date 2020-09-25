package com.redescooter.ses.web.ros.vo.restproduct;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassNameRosPartsExportEnter
 * @Description
 * @Author Aleks
 * @Date2020/9/25 13:29
 * @Version V1.0
 **/
@Data
public class RosPartsExportEnter implements Serializable {

    @Excel(name = "PARTS NO",width = 20,needMerge = false)
    private String partsNo;

    @Excel(name = "SEC",width = 20)
    private String partsSecName;

    @Excel(name = "TYPE",width = 20)
    private String type;

    @Excel(name = "SN CLASS",width = 20)
    private String snClass;

    @Excel(name = "ASSEMBLY",width = 20)
    private String assembly;

    @Excel(name = "FOR ASSEMBLY",width = 20)
    private String forAssembly;

    @Excel(name = "CN NAME",width = 20)
    private String cnName;

    @Excel(name = "EN NAME",width = 20)
    private String enName;

    @Excel(name = "FR NAME",width = 20)
    private String frName;

    @Excel(name = "SUPPLIER",width = 20)
    private String supplierName;

    @Excel(name = "LEAD TIME",width = 20)
    private String procurementCycle;



}
