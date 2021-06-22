package com.redescooter.ses.web.ros.vo.sales;

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
public class ImportCustomerExcleData implements Serializable, IExcelModel, IExcelDataModel {

    /**
     * 姓氏
     */
    @Excel(name = "first_name", width = 25)
    private String firstName;

    /**
     * 名字
     */
    @Excel(name = "last_name", width = 25)
    private String lastName;


    /**
     * 区号
     */
    @Excel(name = "area_code", width = 25)
    private String areaCode;

    /**
     * 手机号码
     */
    @Excel(name = "phone", width = 25)
    private String phone;

    /**
     * 邮箱
     */
    @Excel(name = "email", width = 25)
    private String email;


    @Excel(name = "country_code", width = 25)
    private String countryCode;


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
