package com.redescooter.ses.web.ros.vo.restproduct;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.handler.inter.IExcelDataModel;
import cn.afterturn.easypoi.handler.inter.IExcelModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassNameRosPraseExcelData
 * @Description
 * @Author Aleks
 * @Date2020/9/25 18:41
 * @Version V1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RosParseExcelData implements Serializable, IExcelModel, IExcelDataModel {

    @Excel(name = "Level",width = 20)
    private String level;

    @Excel(name = "PARTS_NO",width = 20)
    private String partsNo;

    @Excel(name = "Main_Drawing",width = 20)
    private String mainDrawing;

    @Excel(name = "Chinese_Name",width = 20)
    private String chineseName;

    @Excel(name = "English_Name")
    private String englishName;

    @Excel(name = "ECN_Number",width = 20)
    private String ecnNumber;

    @Excel(name = "SEC",width = 20)
    private String sec;

    @Excel(name = "ITEM",width = 20)
    private String item;

    @Excel(name = "Sell_Class",width = 20)
    private String sellClass;

    @Excel(name = "TYPE",width = 20)
    private String type;

    @Excel(name = "Drawing_Size",width = 20)
    private String drawingSize;

    @Excel(name = "Weight",width = 20)
    private String weight;

    @Excel(name = "Quantity",width = 20)
    private String quantity;

    @Excel(name = "Supplier1",width = 20)
    private String supplier1;

    @Excel(name = "RATE_TYP",width = 20)
    private String rateTyp;

    @Excel(name = "Supplier2",width = 20)
    private String supplier2;

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
