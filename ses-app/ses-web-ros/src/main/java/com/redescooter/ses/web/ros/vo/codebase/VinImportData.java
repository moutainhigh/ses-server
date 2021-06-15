package com.redescooter.ses.web.ros.vo.codebase;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName VinImportData
 * @Description vin导入数据
 * @Author Charles
 * @Date 2021/06/11 14:20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VinImportData implements Serializable{

    @Excel(name = "座位数", width = 20)
    private Integer seatNumber;

    @Excel(name = "VIN", width = 20)
    private String vin;
}
