package com.redescooter.ses.web.delivery.vo.excel;

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
public class ExpressOrderExcleData implements Serializable, IExcelModel, IExcelDataModel {

    /**
     * 客户id，等价于收件人名称
     */
    @Excel(name = "Customer_Reference")
    private String customerReference;

    /**
     * 发货人
     */
    @Excel(name = "Sender_name")
    private String senderName;

    /**
     * 发货方公司，个人时为空
     */
    @Excel(name = "Sender_Company")
    private String senderCompany;

    /**
     * 发货人邮箱
     */
    @Excel(name = "Sender_Email")
    private String senderMail;

    /**
     * 发货人手机
     */
    @Excel(name = "Sender_Phone")
    private String senderPhone;

    /**
     * 发货方详细地址
     */
    @Excel(name = "Sender_Address_Line")
    private String senderAddress;

    /**
     * 发货方城市
     */
    @Excel(name = "Sender_City_Town")
    private String senderCity;

    /**
     * 发货方邮编
     */
    @Excel(name = "Sender_Postal_Code")
    private String senderPostcode;
    /**
     * 发货方省份
     */
    @Excel(name = "Sender_State_Province")
    private String senderProvince;
    /**
     * 发货方国家
     */
    @Excel(name = "Sender_Country")
    private String senderCountry;

    /**
     * 发货方纬度
     */
    @Excel(name = "Sender_Latitude")
    private BigDecimal senderLatitude;

    /**
     * 发货方经度
     */
    @Excel(name = "Sender_Longitude")
    private BigDecimal senderLongitude;

    /**
     * 发货方备注
     */
    @Excel(name = "Sender_Notes")
    private String senderNotes;

    /**
     * 收件人
     */
    @Excel(name = "Recipient_name")
    private String recipientName;

    /**
     * 收件方公司，个人时为空
     */
    @Excel(name = "Recipient_Company")
    private String recipientCompany;

    /**
     * 收件方邮箱
     */
    @Excel(name = "Recipient_Email")
    private String recipientMail;

    /**
     * 收件人电话
     */
    @Excel(name = "Recipient_Phone")
    private String recipientPhone;

    /**
     * 收件方详细地址
     */
    @Excel(name = "Recipient_Address_Line")
    private String recipientAddress;

    /**
     * 收件方城市
     */
    @Excel(name = "Recipient_City_Town")
    private String recipientCity;

    /**
     * 收件方邮编
     */
    @Excel(name = "Recipient_Postal_Code")
    private String recipientPostcode;

    /**
     * 收件方省份
     */
    @Excel(name = "Recipient_State_Province")
    private String recipientProvince;

    /**
     * 收货方国家
     */
    @Excel(name = "Recipient_Country")
    private String recipientCountry;

    /**
     * 收件方纬度
     */
    @Excel(name = "Recipient_Latitude")
    private BigDecimal recipientLatitude;

    /**
     * 收货方经度
     */
    @Excel(name = "Recipient_Longitude")
    private BigDecimal recipientLongitude;

    /**
     * 收件方备注
     */
    @Excel(name = "Recipient_Notes")
    private String recipientNotes;

    /**
     * 期望送达时间,注意这是时间段
     */
    @Excel(name = "Delivery_After", databaseFormat = "yyyy-MM-dd HH:mm:ss", format = "yyyy-MM-dd HH:mm:ss")
    private Date expectTimeBegin;

    /**
     * 期望送达时间,注意这是时间段
     */
    @Excel(name = "Delivery_Before", databaseFormat = "yyyy-MM-dd HH:mm:ss", format = "yyyy-MM-dd HH:mm:ss")
    private Date expectTimeEnd;

    /**
     * 车辆类型
     */
    @Excel(name = "Vehicle_Type")
    private String vehicleType;

    /**
     * 其他备注
     */
    @Excel(name = "General_Notes")
    private String generalNotes;

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
    public int getRowNum() {
        return this.rowNum;
    }

    /**
     * 设置行号
     *
     * @param rowNum
     */
    @Override
    public void setRowNum(int rowNum) {
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
