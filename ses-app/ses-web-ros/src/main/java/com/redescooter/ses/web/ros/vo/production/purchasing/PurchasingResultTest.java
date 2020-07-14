package com.redescooter.ses.web.ros.vo.production.purchasing;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.constant.DateConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @ClassNamePurchasingResultTest
 * @Description
 * @Author Aleks
 * @Date2020/7/10 18:32
 * @Version V1.0
 **/
@Data
public class PurchasingResultTest {

    //@ApiModelProperty(value = "id")
    @Excel(name = "id")
    private Long id;

    //@ApiModelProperty(value = "合同编号")
    @Excel(name = "合同编号")
    private String contractN;

//    //@ApiModelProperty(value = "状态")
      @Excel(name = "状态")
      private String status;
//
//    //@ApiModelProperty(value = "收货人Id")
      @Excel(name = "收货人Id")
      private Long consigneeId;
//
//    //@ApiModelProperty(value = "收货人名字")
      @Excel(name = "收货人名字")
      private String consigneeFirstName;
//
//    //@ApiModelProperty(value = "收货人名字")
      @Excel(name = "收货人名字")
      private String consigneeLastName;
//
//    //@ApiModelProperty(value = "收货人电话国家代码")
      @Excel(name = "收货人电话国家代码")
      private String consigneeCountryCode;
//
//    //@ApiModelProperty(value = "收货人电话")
      @Excel(name = "收货人电话")
      private String consigneePhone;
//
//    //@ApiModelProperty(value = "收货人邮箱")
      @Excel(name = "收货人邮箱")
      private String consigneeEmail;
//
//    //@ApiModelProperty(value = "工厂Id")
      @Excel(name = "工厂Id")
      private Long factoryId;
//
//    //@ApiModelProperty(value = "工常名字")
      @Excel(name = "工常名字")
      private String factoryName;
//
//    //@ApiModelProperty(value = "工厂联系人名字")
      @Excel(name = "工厂联系人名字")
      private String factoryContactFirstName;
//
//    //@ApiModelProperty(value = "工厂联系人名字")
      @Excel(name = "工厂联系人名字")
      private String factoryContactLastName;
//
//    //@ApiModelProperty(value = "工厂联系人名字")
      @Excel(name = "工厂联系人名字")
      private String factoryContactFullName;
//
//    //@ApiModelProperty(value = "联系人电话国家代码")
      @Excel(name = "联系人电话国家代码")
      private String factoryContactPhoneCountryCode;
//
//    //@ApiModelProperty(value = "联系人电话")
      @Excel(name = "联系人电话")
      private String factoryContactPhone;
//
//    //@ApiModelProperty(value = "联系人邮箱")
      @Excel(name = "联系人邮箱")
      private String factoryContactEmail;
//
//    //@ApiModelProperty(value = "总价格")
      @Excel(name = "总价格")
      private String totalPrice;
//
//    //@ApiModelProperty(value = "付款类型")
      @Excel(name = "付款类型")
      private String paymentType;
//
//
//    //@ApiModelProperty(value = "天数")
      @Excel(name = "天数")
      private Integer days;
//
//    //@ApiModelProperty(value = "分期总数")
      @Excel(name = "分期总数")
      private Integer stagTotal;
//
//    //@ApiModelProperty(value = "支付分期数")
      @Excel(name = "支付分期数")
      private Integer paidstagNum;
//
//    //@ApiModelProperty(value = "部件数量")
      @Excel(name = "部件数量")
      private Integer partsQty;


}
