package com.redescooter.ses.web.ros.vo.production.assembly;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassNameAssemblyExportResult
 * @Description
 * @Author Aleks
 * @Date2020/7/27 15:52
 * @Version V1.0
 **/
@Data
public class AssemblyExportResult {

    @Excel(name = "组装单编号")
    private String contractN;

    @Excel(name = "状态")
    private String status;


    @Excel(name = "工厂名字")
    private String factoryName;


    @Excel(name = "联系人姓名")
    private String contactFullName;

    @Excel(name = "联系人电话")
    private String contactPhone;

    @Excel(name = "联系人电话代码")
    private String contactPhoneCountryCode;

    @Excel(name = "联系人邮箱")
    private String contactEmail;

    @Excel(name = "数量")
    private Integer qty;

    @Excel(name = "创建时间")
    private String createdTime;

    @Excel(name = "收货人姓名")
    private String consigneeFullName;

    @Excel(name = "收货人电话")
    private String consigneePhone;

    @Excel(name = "收货人电话代码")
    private String consigneePhoneCountryCode;

    @Excel(name = "收货人邮箱")
    private String consigneeEmail;

    @Excel(name = "总价格")
    private BigDecimal totalPrice;

    @Excel(name = "对帐日")
    private String statementDate;

    @Excel(name = "天数")
    private Integer days;

    @Excel(name = "分期总数")
    private Integer stagTotal;

    @Excel(name = "支付分期数")
    private Integer paidstagNum;

    @Excel(name = "产品价格")
    private BigDecimal productPrice;

    @Excel(name = "加工费")
    private BigDecimal processCost;

    @Excel(name = "加工费比例")
    private BigDecimal processCostRatio;



}
