package com.redescooter.ses.web.ros.vo.codebase;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.handler.inter.IExcelDataModel;
import cn.afterturn.easypoi.handler.inter.IExcelModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName RsnImportData
 * @Description rsn导入数据
 * @Author Charles
 * @Date 2021/06/11 14:17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RsnImportData implements Serializable, IExcelModel, IExcelDataModel {

    @Excel(name = "RSN", width = 20)
    private String rsn;

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
