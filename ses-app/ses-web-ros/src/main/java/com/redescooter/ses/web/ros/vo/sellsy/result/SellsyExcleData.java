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

    @Excel(name = "Etat", width = 25)
    public String etat;

    @Excel(name = "Numéro", width = 25)
    public String invoice_num;

    @Excel(name = "Date", width = 25)
    public String invoice_date;

    @Excel(name = "Client", width = 25)
    public String client;

    @Excel(name = "Référence", width = 25)
    public String invoice_notes;

    @Excel(name = "Montant HT", width = 25)
    public String ht;

    @Excel(name = "Montant TTC", width = 25)
    public String ttc;

    @Excel(name = "Réglé", width = 25)
    public String receivePayment;

    @Excel(name = "Reste dû", width = 25)
    public String remainingPayment;

    @Excel(name = "Adresse de livraison du client", width = 25)
    public String clientAddress;

    @Excel(name = "Note du payeur", width = 25)
    public String buyerNotes;

    @Excel(name = "Remarques sur le produit", width = 25)
    public String productNodes;

    @Excel(name = "Numéro de produit", width = 25)
    public String productNum;

    @Excel(name = "nom du produit", width = 25)
    public String productName;

    @Excel(name = "TVA", width = 25)
    public String tva;

    @Excel(name = "Qté", width = 25)
    public String productQty;

    @Excel(name = "Unité", width = 25)
    public String productQtyUnit;

    @Excel(name = "Rem", width = 25)
    public String discount;

    @Excel(name = "PdfUrl", width = 25)
    public String pdfUrl;

    @Excel(name = "unit_price", width = 25)
    public String unitPrice;

    @Excel(name = "is_parent", width = 25)
    public String isParent;

    /**
     * 解析错误信息
     */
    private String errorMsg;
    /**
     * 错误行号
     */
    private int rowNum;

    public static final String ETAT = "Etat";
    public static final String NUMÉRO = "Numéro";
    public static final String DATE = "Date";
    public static final String CLIENT = "Client";
    public static final String RÉFÉRENCE = "Référence";
    public static final String MONTANT_HT = "Montant HT";
    public static final String MONTANT_TTC = "Montant TTC";
    public static final String RECEIVE_PAYMENT = "Réglé";
    public static final String REMAINING_PAYMENT = "Reste dû";
    public static final String CLIENT_ADDRESS = "Adresse de livraison du client";
    public static final String BUYER_NOTES = "Note du payeur";
    public static final String PRODUCT_NODES = "Remarques sur le produit";
    public static final String PRODUCT_NUM = "Numéro de produit";
    public static final String PRODUCT_NAME = "nom du produit";
    public static final String TVA = "TVA";
    public static final String PRODUCT_QTY = "Qté";
    public static final String PRODUCT_QTY_UNIT = "Unité";
    public static final String DISCOUNT = "Rem";
    public static final String PDF_URL = "PdfUrl";
    public static final String UNIT_PRICE = "unit_price";
    public static final String IS_PARENT = "is_parent";


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
