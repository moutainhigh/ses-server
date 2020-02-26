package com.redescooter.ses.web.ros.vo.bom.parts;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.handler.inter.IExcelDataModel;
import cn.afterturn.easypoi.handler.inter.IExcelModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 18/9/2019 1:32 下午
 * @ClassName: ExpressOrderExcleData
 * @Function: TODO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpressPartsExcleData implements Serializable, IExcelModel, IExcelDataModel {

    /**
     * 部品号
     */
    @Excel(name = "PartsN", width = 25)
    private String partsN;

    /**
     * 项目区域
     */
    @Excel(name = "ESC", width = 25)
    private String esc;

    /**
     * 类型
     */
    @Excel(name = "Type", width = 25)
    private String type;

    /**
     * 是否可销售
     */
    @Excel(name = "SN CLASS", width = 25)
    private String snClass;

    /**
     * 英文名称
     */
    @Excel(name = "EN Name", width = 25)
    private String enName;

    /**
     * 法文名称
     */
    @Excel(name = "FR Name", width = 25)
    private String frName;

    /**
     * 中文名称
     */
    @Excel(name = "CN Name", width = 25)
    private String cnName;

    /**
     * 解析错误信息
     */
    private String errorMsg;
    /**
     * 错误行号
     */
    private int rowNum;

    public static final String PARTS_N = "Parts N";
    public static final String ESC = "ESC";
    public static final String TYPE = "Type";
    public static final String SN_CLASS = "SN CLASS";
    public static final String EN_NAME = "EN Name";
    public static final String FR_NAME = "Sender_Address_Line";
    public static final String CN_NAME = "Sender_City_Town";

    /**
     * 获取行号
     *
     * @return
     */
    @Override
    public Integer getRowNum() {
        return this.rowNum;
    }

    /**
     * 设置行号
     *
     * @param rowNum
     */
    @Override
    public void setRowNum(Integer rowNum) {
        this.rowNum = rowNum;
    }

    /**
     * 获取错误数据
     *
     * @return
     */
    @Override
    public String getErrorMsg() {
        return this.errorMsg;
    }

    /**
     * 设置错误信息
     *
     * @param errorMsg
     */
    @Override
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

}
