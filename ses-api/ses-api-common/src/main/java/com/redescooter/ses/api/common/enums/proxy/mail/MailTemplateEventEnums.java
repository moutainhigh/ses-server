package com.redescooter.ses.api.common.enums.proxy.mail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 11/10/2019 4:25 下午
 * @ClassName: MailTemplateEventEnums
 * @Function: TODO
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum MailTemplateEventEnums {

    WEB_ACTIVATE("WEB激活", 1, "web_activate", "WEB_ACTIVATE"),
    MOBILE_ACTIVATE("MOBILE激活", 2, "mobile_activate", "MOBILE_ACTIVATE"),
    WEB_PASSWORD("WEB密码", 3, "web_password", "WEB_PASSWORD"),
    MOBILE_PASSWORD("MOBILE密码", 4, "mobile_password", "MOBILE_PASSWORD"),
    WEB_FREEZE_WARN("WEB冻结通知", 5, "web_freeze_warn", "WEB_FREEZE_WARN"),
    WEB_RENEWAL_WARN("WEB续约通知", 6, "web_renewal_warn", "WEB_RENEWAL_WARN"),
    WEB_UNFREEZE_WARN("WEB解冻通知", 7, "web_unfreeze_warn", "WEB_UNFREEZE_WARN"),
    WEB_EXPIRED_WARN("WEB过期通知", 8, "web_expired_warn", "WEB_EXPIRED_WARN"),
    MOBILE_PERMISSION_WARN("MOBILE权限通知", 9, "mobile_permission_warn", "MOBILE_PERMISSION_WARN"),
    CUSTOMER_INQUIRY_PAY_DEPOSIT("客户询价单支付定金", 10, "customer_inquiry_pay_deposit", "CUSTOMER_INQUIRY_PAY_DEPOSIT"),
    CUSTOMER_INQUIRY_LAST_PARAGRAPH("客户询价单支付尾款", 11, "customer_inquiry_last_paragraph", "CUSTOMER_INQUIRY_LAST_PARAGRAPH"),
    SUBSCRIPTION_PAY_SUCCEED_SEND_EAMIL("订金支付成功发送邮箱", 12, "subscription_pay_succeed_send_eamil", "SUBSCRIPTION_PAY_SUCCEED_SEND_EAMIL"),
    FORGET_PSD_SEND_MAIL("官网忘记密码发送邮件", 13, "forget_psd_send_mail", "FORGET_PSD_SEND_MAIL"),
    SUBSCRIBE_TO_EMAIL_SUCCESSFULLY("官网订邮件成功后发送邮件", 14, "Subscribe_to_email_successfully", "SUBSCRIBE_TO_EMAIL_SUCCESSFULLY"),
    ROS_CREATE_EMPLOYEE("ROS员工账户开通", 15, "Ros_open_employee_account", "ROS_OPEN_EMPLOYEE_ACCOUNT"),
    ROS_FORGET_PSD_SEND_MAIL("ROS忘记密码发送邮件", 16, "ros_forget_psd_send_mail", "ROS_FORGET_PSD_SEND_MAIL"),
    WEBSITE_SIGN_UP("WEBSITE_SIGN_UP",17,"website_sign_up","WEBSITE_SIGN_UP"),
    //区域人员 是18
    ROS_CONTACTUS_REPLY_MESSAGE("ROS联系我们回复消息", 19, "ros_contactus_reply_message", "ROS_CONTACTUS_REPLY_MESSAGE"),
    ROS_LOGIN_BY_CODE("ROS发送验证码登录", 20, "ros_login_by_code", "ROS_LOGIN_BY_CODE"),
    SAAS_LOGIN_BY_CODE("SAAS发送验证码登录", 21, "saas_login_by_code", "SAAS_LOGIN_BY_CODE"),
    CONSTANT_US_NOTICE("联系我们邮件通知", 22, "constant_us_notice", "CONSTANT_US_NOTICE"),
    ENTRUST_SIGN_TO_CONSIGNOR("委托单签收给发货人的邮件",23,"entrust_sign_to_consignor","ENTRUST_SIGN_TO_CONSIGNOR"),
    ENTRUST_SIGN_TO_CONSIGNEE("委托单签收给收货人的邮件",24,"entrust_sign_to_consignee","ENTRUST_SIGN_TO_CONSIGNEE"),
    ENTRUST_DELIVERY_TO_CONSIGNEE("委托单发货给收货人的邮件",25,"entrust_delivery_to_consignee","ENTRUST_DELIVERY_TO_CONSIGNEE"),
    OMS_FORGET_PSD_SEND_MAIL("OMS忘记密码发送邮件", 26, "oms_forget_psd_send_mail", "OMS_FORGET_PSD_SEND_MAIL"),
    ;
    private String mome;

    private int templateNo;

    private String name;

    private String event;


    public static MailTemplateEventEnums getMailTemplateEventEnum(int templateNo) {
        for (MailTemplateEventEnums item : MailTemplateEventEnums.values()) {
            if (item.getTemplateNo() == templateNo) {
                return item;
            }
        }
        return null;
    }
}
