package com.redescooter.ses.web.ros.vo.codebase;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.handler.inter.IExcelDataModel;
import cn.afterturn.easypoi.handler.inter.IExcelModel;
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
public class VinImportData  implements Serializable, IExcelModel, IExcelDataModel {

    @Excel(name = "座位数", width = 20)
    private Integer seatNumber;

    @Excel(name = "VIN", width = 20)
    private String vin;

    /**
     * 解析错误信息
     */
    private String errorMsg;
    /**
     * 错误行号
     */
    private int rowNum;

    @Override
    public Integer getRowNum() {
        return null;
    }

    @Override
    public void setRowNum(Integer integer) {

    }

    @Override
    public String getErrorMsg() {
        return null;
    }

    @Override
    public void setErrorMsg(String s) {

    }
}
