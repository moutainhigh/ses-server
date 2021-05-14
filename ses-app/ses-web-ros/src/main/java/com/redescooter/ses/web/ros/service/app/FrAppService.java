package com.redescooter.ses.web.ros.service.app;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.TokenResult;
import com.redescooter.ses.web.ros.dm.OpeWarehouseAccount;
import com.redescooter.ses.web.ros.vo.app.AppLoginEnter;
import com.redescooter.ses.web.ros.vo.app.BindVinEnter;
import com.redescooter.ses.web.ros.vo.app.InputBatteryEnter;
import com.redescooter.ses.web.ros.vo.app.InputScooterEnter;
import com.redescooter.ses.web.ros.vo.app.InquiryDetailEnter;
import com.redescooter.ses.web.ros.vo.app.InquiryDetailResult;
import com.redescooter.ses.web.ros.vo.app.InquiryListAppEnter;
import com.redescooter.ses.web.ros.vo.app.InquiryListResult;
import com.redescooter.ses.web.ros.vo.assign.tobe.enter.CustomerIdEnter;

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

}
