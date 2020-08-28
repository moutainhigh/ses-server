package com.redescooter.ses.web.ros.constant;

/**
 * @ClassName:SellsyMethodConstant
 * @description: SellsyMethodConstant
 * @author: Alex
 * @Version：1.3
 * @create: 2020/08/24 14:44
 */
public interface SellsyMethodConstant {

    //客户模块
    
    String Client_List="Client.getList";
    
    String Client_GetOne="Client.getOne";
    
    String Client_GetAddress="Client.getAddress";
    
    String Client_GetContact="Client.getContact";
    
    String Client_GetBillingContact="Client.getBillingContact";
    
    String Client_Create="Client.create";
    
    String Client_Update="Client.update";
    
    String Client_Delete="Client.delete";
    
    String Client_UpdateOwner="Client.updateOwner";
    
    String Client_AddAddress="Client.addAddress";
    
    String Client_addContact="Client.addContact";
    
    String Client_UpdateAddress="Client.updateAddress";
    
    String Client_UpdateContact="Client.updateContact";
    
    String Client_DeleteAddress="Client.deleteAddress";
    
    String Client_DeleteContact="Client.deleteContact";
    
    String Client_UpdateContactPicture="Client.updateContactPicture";
    
    String Client_UpdatePrefs="Client.updatePrefs";
    
    String Client_UpdateThirdPicture="Client.updateThirdPicture";
    
    String Client_UpdateSharingGroups="Client.updateSharingGroups";
    
    String Client_GetMargin="Client.getMargin";
    
    
    // 单据列表
    String Document_GetList="Document.getList";

    // 查询指定单据
    String Document_GetOne="Document.getOne";

    // 单据创建
    String Document_Create="Document.create";

    // 获取货币列表
    String AccountPrefs_GetCurrencies = "AccountPrefs.getCurrencies";

    // 获取当前公司使用的货币
    String AccountPrefs_GetCurrency = "AccountPrefs.getCurrency";

    // 地址列表
    String AccountPrefs_GetAddressList = "AccountPrefs.getAddressList";

    // 查询指定地址
    String AccountPrefs_GetAddress = "AccountPrefs.getAddress";

    // 账户设置的布局列表
    String Accountdatas_GetDocLayouts = "Accountdatas.getDocLayouts";

    // 获取支付日期列表
    String Accountdatas_GetPayDates = "Accountdatas.getPayDates";

    // 关税类型
    String Accountdatas_GetRateCategories = "Accountdatas.getRateCategories";

    // 查询指定的关税类型
    String Accountdatas_GetRateCategory = "Accountdatas.getRateCategory";

    // 账户翻译语言，列表
    String Accountdatas_GetTranslationLanguages = "Accountdatas.getTranslationLanguages";

    // 获取税率列表
    String Accountdatas_GetTaxes = "Accountdatas.getTaxes";

    // 查询指定税率
    String Accountdatas_GetTaxe = "Accountdatas.getTaxe";

    // 获取计量单位列表
    String Accountdatas_GetUnits = "Accountdatas.getUnits";

    // 计量单位
    String Accountdatas_GetUnit = "Accountdatas.getUnit";

    // 物流供应商
    String Accountdatas_GetShippingList = "Accountdatas.getShippingList";

    // 查询指定物流供应商
    String Accountdatas_GetShipping = "Accountdatas.getShipping";

    // 产品列表
    String Catalogue_GetList = "Catalogue.getList";

    // 产品详情
    String Catalogue_GetOne = "Catalogue.getOne";

    // 员工列表
    String Staffs_GetList = "Staffs.getList";

    String Staffs_GetGroups = "Staffs.getGroups";
}
