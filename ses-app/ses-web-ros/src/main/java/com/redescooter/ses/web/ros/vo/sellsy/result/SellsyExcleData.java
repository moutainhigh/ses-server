package com.redescooter.ses.web.ros.vo.sellsy.result;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.handler.inter.IExcelDataModel;
import cn.afterturn.easypoi.handler.inter.IExcelModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

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
public class SellsyExcleData implements Serializable, IExcelModel, IExcelDataModel {

    @Excel(name = "Type de doc", width = 25)
    public String status;

    @Excel(name = "N° pièce", width = 25)
    public String invoice_num;

    @Excel(name = "invoice_time", width = 25)
    public String invoice_date;

    @Excel(name = "Nom Client", width = 25)
    public String client_name;

    @Excel(name = "Taux TVA", width = 25)
    public String tva;

    @Excel(name = "Total HT", width = 25)
    public String ht;

    @Excel(name = "Total TTC", width = 25)
    public String ttc;

    @Excel(name = "Statut", width = 25)
    public String payStatus;

    @Excel(name = "pay_time", width = 25)
    public String payTime;

    @Excel(name = "Moyen de paiement", width = 25)
    public String payType;

    @Excel(name = "PDF", width = 25)
    private String url;

    @Excel(name = "Référence", width = 25)
    private String remark;
    /**
     * 解析错误信息
     */
    private String errorMsg;
    /**
     * 错误行号
     */
    private int rowNum;

    public static final String STATUT = "Type de doc";
    public static final String INVOICE_NUM = "N° pièce";
    public static final String CLIENT_NAME = "Nom Client";
    public static final String TIME = "Date Pièce";
    public static final String TOTALHT = "Total HT";
    public static final String TVA = "Taux TVA";
    public static final String TOTALTTC = "Total TTC";
    public static final String PAY_STATUS = "Statut";
    public static final String PAY_TYPE = "Moyen de paiement";
    public static final String PAY_TIME = "Date de paiement";


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
