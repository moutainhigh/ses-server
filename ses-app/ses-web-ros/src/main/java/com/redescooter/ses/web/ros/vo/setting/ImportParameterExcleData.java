package com.redescooter.ses.web.ros.vo.setting;

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
public class ImportParameterExcleData implements Serializable, IExcelModel, IExcelDataModel {

    /**
     * 参数名称
     */
    @Excel(name = "Parameter_Name", width = 25)
    private String parameterName;

    /**
     * 分组名称
     */
    @Excel(name = "Group_Name", width = 25)
    private String groupName;

    /**
     * key 值
     */
    @Excel(name = "Key", width = 25)
    private String key;

    /**
     * value
     */
    @Excel(name = "Value", width = 25)
    private String value;

    /**
     * 是否启用
     */
    @Excel(name = "Enable", width = 25)
    private Boolean enable;

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
