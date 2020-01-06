package com.redescooter.ses.tool.utils.accountType;

import com.redescooter.ses.api.common.enums.base.AccountTypeEnums;
import com.redescooter.ses.api.common.enums.base.AppIDEnums;
import com.redescooter.ses.api.common.enums.customer.CustomerIndustryEnums;
import com.redescooter.ses.api.common.enums.customer.CustomerTypeEnum;
import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName:AccountTypeUtils
 * @description: AccountTypeUtils
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/23 14:46
 */
public class AccountTypeUtils {

    public static Integer getAccountType(String customerType, String industryType) {

        if (StringUtils.equals(CustomerTypeEnum.ENTERPRISE.getValue(), customerType)
                && StringUtils.equals(CustomerIndustryEnums.RESTAURANT.getValue(), industryType)) {
            return AccountTypeEnums.WEB_RESTAURANT.getAccountType();
        }

        if (StringUtils.equals(CustomerTypeEnum.ENTERPRISE.getValue(), customerType)
                && StringUtils.equals(CustomerIndustryEnums.EXPRESS_DELIVERY.getValue(), industryType)) {
            return AccountTypeEnums.WEB_EXPRESS.getAccountType();
        }

        if (StringUtils.equals(CustomerTypeEnum.PERSONAL.getValue(), customerType)) {
            return AccountTypeEnums.APP_PERSONAL.getAccountType();
        }
        if (StringUtils.equals(CustomerTypeEnum.ENTERPRISE.getValue(), customerType)
                && StringUtils.equals(CustomerIndustryEnums.EXPRESS_DELIVERY.getValue(), industryType)) {
            return AccountTypeEnums.APP_EXPRESS.getAccountType();
        }
        if (StringUtils.equals(CustomerTypeEnum.ENTERPRISE.getValue(), customerType)
                && StringUtils.equals(CustomerIndustryEnums.RESTAURANT.getValue(), industryType)) {
            return AccountTypeEnums.APP_RESTAURANT.getAccountType();
        }
        return null;
    }

    public static String getAppId(Integer accountType) {

        if (AccountTypeEnums.WEB_EXPRESS.getAccountType() == accountType) {
            return AppIDEnums.getAppIdValue(AccountTypeEnums.WEB_EXPRESS.getAppId());
        }

        if (AccountTypeEnums.WEB_RESTAURANT.getAccountType() == accountType) {
            return AppIDEnums.getAppIdValue(AccountTypeEnums.WEB_RESTAURANT.getAppId());
        }

        if (AccountTypeEnums.APP_EXPRESS.getAccountType() == accountType) {
            return AppIDEnums.getAppIdValue(AccountTypeEnums.APP_EXPRESS.getAppId());
        }

        if (AccountTypeEnums.APP_RESTAURANT.getAccountType() == accountType) {
            return AppIDEnums.getAppIdValue(AccountTypeEnums.APP_RESTAURANT.getAppId());
        }
        if (AccountTypeEnums.APP_PERSONAL.getAccountType() == accountType) {
            return AppIDEnums.getAppIdValue(AccountTypeEnums.APP_PERSONAL.getAppId());
        }
        return null;
    }

    public static String getSystemId(Integer accountType) {

        if (AccountTypeEnums.WEB_EXPRESS.getAccountType() == accountType) {
            return AccountTypeEnums.WEB_EXPRESS.getSystemId();
        }

        if (AccountTypeEnums.WEB_RESTAURANT.getAccountType() == accountType) {
            return AccountTypeEnums.WEB_RESTAURANT.getSystemId();
        }

        if (AccountTypeEnums.APP_EXPRESS.getAccountType() == accountType) {
            return AccountTypeEnums.APP_EXPRESS.getSystemId();
        }

        if (AccountTypeEnums.APP_RESTAURANT.getAccountType() == accountType) {
            return AccountTypeEnums.APP_RESTAURANT.getSystemId();
        }

        if (AccountTypeEnums.APP_PERSONAL.getAccountType() == accountType) {
            return AccountTypeEnums.APP_PERSONAL.getSystemId();
        }
        return null;
    }

}
