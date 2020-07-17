package com.redescooter.ses.web.ros.vo.production.purchasing;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * @ClassNamePurchasingResultTest
 * @Description
 * @Author Aleks
 * @Date2020/7/10 18:32
 * @Version V1.0
 **/
@Data
public class PurchasingExportResult {

    @Excel(name = "合同编号")
    private String contractN;

      @Excel(name = "收货人Id")
      private Long consigneeId;

      @Excel(name = "收货人名字")
      private String consigneeFirstName;

      @Excel(name = "收货人名字")
      private String consigneeLastName;

      @Excel(name = "收货人电话国家代码")
      private String consigneeCountryCode;

      @Excel(name = "收货人电话")
      private String consigneePhone;

      @Excel(name = "收货人邮箱")
      private String consigneeEmail;

      @Excel(name = "工厂Id")
      private Long factoryId;

      @Excel(name = "工常名字")
      private String factoryName;

      @Excel(name = "工厂联系人名字")
      private String factoryContactFirstName;

      @Excel(name = "工厂联系人名字")
      private String factoryContactLastName;

      @Excel(name = "工厂联系人名字")
      private String factoryContactFullName;

      @Excel(name = "联系人电话国家代码")
      private String factoryContactPhoneCountryCode;

      @Excel(name = "联系人电话")
      private String factoryContactPhone;

      @Excel(name = "联系人邮箱")
      private String factoryContactEmail;

      @Excel(name = "总价格")
      private String totalPrice;

      @Excel(name = "天数")
      private Integer days;

      @Excel(name = "分期总数")
      private Integer stagTotal;

      @Excel(name = "支付分期数")
      private Integer paidstagNum;

      @Excel(name = "部件数量")
      private Integer partsQty;


}
