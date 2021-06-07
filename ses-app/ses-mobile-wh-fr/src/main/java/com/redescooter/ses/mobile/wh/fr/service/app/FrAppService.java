package com.redescooter.ses.mobile.wh.fr.service.app;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.TokenResult;
import com.redescooter.ses.mobile.wh.fr.dm.OpeWarehouseAccount;
import com.redescooter.ses.mobile.wh.fr.vo.AppLoginEnter;
import com.redescooter.ses.mobile.wh.fr.vo.BindLicensePlateEnter;
import com.redescooter.ses.mobile.wh.fr.vo.BindVinEnter;
import com.redescooter.ses.mobile.wh.fr.vo.CustomerIdEnter;
import com.redescooter.ses.mobile.wh.fr.vo.InputBatteryEnter;
import com.redescooter.ses.mobile.wh.fr.vo.InputScooterEnter;
import com.redescooter.ses.mobile.wh.fr.vo.InquiryDetailEnter;
import com.redescooter.ses.mobile.wh.fr.vo.InquiryDetailResult;
import com.redescooter.ses.mobile.wh.fr.vo.InquiryListAppEnter;
import com.redescooter.ses.mobile.wh.fr.vo.InquiryListResult;


/**
 * @Description
 * @Author Chris
 * @Date 2021/5/10 13:57
 */
public interface FrAppService {

    /**
     * 登录
     */
    TokenResult login(AppLoginEnter enter);

    /**
     * 登出
     */
    GeneralResult logout(GeneralEnter enter);

    /**
     * 获得个人信息
     */
    OpeWarehouseAccount getUserInfo(GeneralEnter enter);

    /**
     * 询价单列表
     */
    PageResult<InquiryListResult> getList(InquiryListAppEnter enter);

    /**
     * 询价单详情
     */
    InquiryDetailResult getDetail(InquiryDetailEnter enter);

    /**
     * 录入车辆
     */
    GeneralResult inputScooter(InputScooterEnter enter);

    /**
     * 录入电池
     */
    GeneralResult inputBattery(InputBatteryEnter enter);

    /**
     * 绑定VIN
     */
    GeneralResult bindVin(BindVinEnter enter);

    /**
     * 设置软体
     */
    GeneralResult setScooterModel(CustomerIdEnter enter);

    /**
     * 绑定车牌
     */
    GeneralResult bindLicensePlate(BindLicensePlateEnter enter);

    /**
     * 校验询价单是否被操作过
     */
    //BooleanResult checkOperation(CustomerIdEnter enter);

}
