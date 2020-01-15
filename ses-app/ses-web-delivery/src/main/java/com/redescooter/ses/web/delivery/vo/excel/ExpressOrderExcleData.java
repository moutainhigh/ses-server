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
    @Excel(name = "Customer_Reference",width = 25)
    private String customerReference;

    /**
     * 发货人
     */
    @Excel(name = "Sender_name",width = 25)
    private String senderName;

    /**
     * 发货方公司，个人时为空
     */
    @Excel(name = "Sender_Company",width = 25)
    private String senderCompany;

    /**
     * 发货人邮箱
     */
    @Excel(name = "Sender_Email",width = 25)
    private String senderMail;

    /**
     * 发货人手机
     */
    @Excel(name = "Sender_Phone",width = 25)
    private String senderPhone;

    /**
     * 发货方详细地址
     */
    @Excel(name = "Sender_Address_Line",width = 25)
    private String senderAddress;

    /**
     * 发货方城市
     */
    @Excel(name = "Sender_City_Town",width = 25)
    private String senderCity;

    /**
     * 发货方邮编
     */
    @Excel(name = "Sender_Postal_Code",width = 25)
    private String senderPostcode;
    /**
     * 发货方省份
     */
    @Excel(name = "Sender_State_Province",width = 25)
    private String senderProvince;
    /**
     * 发货方国家
     */
    @Excel(name = "Sender_Country",width = 25)
    private String senderCountry;

    /**
     * 发货方纬度
     */
    @Excel(name = "Sender_Latitude",width = 25)
    private BigDecimal senderLatitude;

    /**
     * 发货方经度
     */
    @Excel(name = "Sender_Longitude",width = 25)
    private BigDecimal senderLongitude;

    /**
     * 发货方备注
     */
    @Excel(name = "Sender_Notes",width = 25)
    private String senderNotes;

    /**
     * 收件人
     */
    @Excel(name = "Recipient_Name",width = 25)
    private String recipientName;

    /**
     * 收件方公司，个人时为空
     */
    @Excel(name = "Recipient_Company",width = 25)
    private String recipientCompany;

    /**
     * 收件方邮箱
     */
    @Excel(name = "Recipient_Email",width = 25)
    private String recipientMail;

    /**
     * 收件人电话
     */
    @Excel(name = "Recipient_Phone",width = 25)
    private String recipientPhone;

    /**
     * 收件方详细地址
     */
    @Excel(name = "Recipient_Address_Line",width = 25)
    private String recipientAddress;

    /**
     * 收件方城市
     */
    @Excel(name = "Recipient_City_Town",width = 25)
    private String recipientCity;

    /**
     * 收件方邮编
     */
    @Excel(name = "Recipient_Postal_Code",width = 25)
    private String recipientPostcode;

    /**
     * 收件方省份
     */
    @Excel(name = "Recipient_State_Province",width = 25)
    private String recipientProvince;

    /**
     * 收货方国家
     */
    @Excel(name = "Recipient_Country",width = 25)
    private String recipientCountry;

    /**
     * 收件方纬度
     */
    @Excel(name = "Recipient_Latitude",width = 25)
    private BigDecimal recipientLatitude;

    /**
     * 收货方经度
     */
    @Excel(name = "Recipient_Longitude",width = 25)
    private BigDecimal recipientLongitude;

    /**
     * 收件方备注
     */
    @Excel(name = "Recipient_Notes",width = 25)
    private String recipientNotes;

    /**
     * 期望送达时间,注意这是时间段
     */
    @Excel(name = "Delivery_After", databaseFormat = "yyyy-MM-dd HH:mm:ss", format = "yyyy-MM-dd HH:mm:ss",width = 25)
    private Date expectTimeBegin;

    /**
     * 期望送达时间,注意这是时间段
     */
    @Excel(name = "Delivery_Before", databaseFormat = "yyyy-MM-dd HH:mm:ss", format = "yyyy-MM-dd HH:mm:ss",width = 25)
    private Date expectTimeEnd;

    /**
     * 车辆类型
     */
    @Excel(name = "Vehicle_Type",width = 25)
    private String vehicleType;

    /**
     * 其他备注
     */
    @Excel(name = "General_Notes",width = 25)
    private String generalNotes;

    /**
     * 解析错误信息
     */
    private String errorMsg;
    /**
     * 错误行号
     */
    private int rowNum;

    public static final String CUSTOMER_REFERENCE = "Customer_Reference";
    public static final String SENDER_NAME = "Sender_name";
    public static final String SENDER_COMPANY = "Sender_Company";
    public static final String SENDER_EMAIL = "Sender_Email";
    public static final String SENDER_PHONE = "Sender_Phone";
    public static final String SENDER_ADDRESS_LINE = "Sender_Address_Line";
    public static final String SENDER_CITY_TOWN = "Sender_City_Town";
    public static final String SENDER_POSTAL_CODE = "Sender_Postal_Code";
    public static final String SENDER_STATE_PROVINCE = "Sender_State_Province";
    public static final String SENDER_COUNTRY = "Sender_Country";
    public static final String SENDER_LATITUDE = "Sender_Latitude";
    public static final String SENDER_LONGITUDE = "Sender_Longitude";
    public static final String SENDER_NOTES = "Sender_Notes";
    public static final String RECIPIENT_NAME = "Recipient_Name";
    public static final String RECIPIENT_COMPANY = "Recipient_Company";
    public static final String RECIPIENT_EMAIL = "Recipient_Email";
    public static final String RECIPIENT_PHONE = "Recipient_Phone";
    public static final String RECIPIENT_ADDRESS_LINE = "Recipient_Address_Line";
    public static final String RECIPIENT_CITY_TOWN = "Recipient_City_Town";
    public static final String RECIPIENT_POSTAL_CODE = "Recipient_Postal_Code";
    public static final String RECIPIENT_STATE_PROVINCE = "Recipient_State_Province";
    public static final String RECIPIENT_COUNTRY = "Recipient_Country";
    public static final String RECIPIENT_LATITUDE = "Recipient_Latitude";
    public static final String RECIPIENT_LONGITUDE = "Recipient_Longitude";
    public static final String RECIPIENT_NOTES = "Recipient_Notes";
    public static final String DELIVERY_AFTER = "Delivery_After";
    public static final String DELIVERY_BEFORE = "Delivery_Before";
    public static final String VEHICLE_TYPE = "Vehicle_Type";
    public static final String GENERAL_NOTES = "General_Notes";

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
