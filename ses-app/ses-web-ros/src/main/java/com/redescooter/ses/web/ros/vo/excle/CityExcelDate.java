package com.redescooter.ses.web.ros.vo.excle;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.handler.inter.IExcelDataModel;
import cn.afterturn.easypoi.handler.inter.IExcelModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CityExcelDate implements Serializable, IExcelModel, IExcelDataModel {

    /**
     * 唯一编码
     */
    @Excel(name = "Code_commune_INSEE", width = 25)
    private String id;

    /**
     * 唯一code
     */
    @Excel(name = "Nom_commune", width = 25)
    private String code;

    /**
     * 邮政编码
     */
    @Excel(name = "Code_postal", width = 25)
    private String codePostal;

    /**
     * 城市
     */
    @Excel(name = "Libelle_acheminement", width = 25)
    private String cityName;

    /**
     * 经纬度
     */
    @Excel(name = "coordonnees_gps", width = 25)
    private String gps;

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
