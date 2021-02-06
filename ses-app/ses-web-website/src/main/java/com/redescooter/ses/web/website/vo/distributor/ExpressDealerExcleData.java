package com.redescooter.ses.web.website.vo.distributor;

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
 * @ClassName: ExpressDealerExcleData
 * @Function: TODO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpressDealerExcleData implements Serializable, IExcelModel, IExcelDataModel {

    /**
     * 经销商名称
     */
    @Excel(name = "name", width = 25)
    private String name;

    /**
     * 经销商类型
     */
    @Excel(name = "type", width = 25)
    private String type;

    /**
     * 经销商地址
     */
    @Excel(name = "adresse", width = 25)
    private String adresse;

    /**
     * 邮政编码
     */
    @Excel(name = "cp", width = 25)
    private String cp;

    /**
     * 城市
     */
    @Excel(name = "city", width = 25)
    private String city;

    /**
     * 电话
     */
    @Excel(name = "tel", width = 25)
    private String tel;

    /**
     * 纬度
     */
    @Excel(name = "latitude", width = 25)
    private String latitude;

    /**
     * 经度
     */
    @Excel(name = "longitude", width = 25)
    private String longitude;

    /**
     * 解析错误信息
     */
    private String errorMsg;
    /**
     * 错误行号
     */
    private int rowNum;

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
