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
    CUSTOMER_INQUIRY_PAY_DEPOSIT("客户询价单支付定金",10,"customer_inquiry_pay_deposit","CUSTOMER_INQUIRY_PAY_DEPOSIT"),
    CUSTOMER_INQUIRY_LAST_PARAGRAPH("客户询价单支付尾款",11,"customer_inquiry_last_paragraph","CUSTOMER_INQUIRY_LAST_PARAGRAPH"),
    SUBSCRIPTION_PAY_SUCCEED_SEND_EAMIL("订金支付成功发送邮箱",12,"subscription_pay_succeed_send_eamil","SUBSCRIPTION_PAY_SUCCEED_SEND_EAMIL"),
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
