package com.redescooter.ses.mobile.wh.fr.service.app;

import com.redescooter.ses.api.common.vo.base.BooleanResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.StringEnter;
import com.redescooter.ses.api.common.vo.base.TokenResult;
import com.redescooter.ses.api.common.vo.node.BindVinEnter;
import com.redescooter.ses.api.common.vo.node.InputBatteryEnter;
import com.redescooter.ses.api.common.vo.node.InputScooterEnter;
import com.redescooter.ses.api.common.vo.node.InquiryDetailResult;
import com.redescooter.ses.api.common.vo.node.InquiryListAppEnter;
import com.redescooter.ses.api.common.vo.node.InquiryListResult;
import com.redescooter.ses.api.common.vo.node.SetModelEnter;
import com.redescooter.ses.mobile.wh.fr.dm.OpeWarehouseAccount;
import com.redescooter.ses.mobile.wh.fr.vo.AppLoginEnter;

import java.util.List;
import java.util.Map;

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
     * 列表
     */
    PageResult<InquiryListResult> getList(InquiryListAppEnter enter);

    /**
     * 详情
     */
    InquiryDetailResult getDetail(StringEnter enter);

    /**
     * 根据rsn带出其他6个码
     */
    Map<String, String> getOtherCode(StringEnter enter);

    /**
     * 录入车辆
     */
    GeneralResult inputScooter(InputScooterEnter enter);

    /**
     * 录入电池
     */
    List<String> inputBattery(InputBatteryEnter enter);

    /**
     * 绑定VIN
     */
    GeneralResult bindVin(BindVinEnter enter);

    /**
     * 设置软体
     */
    GeneralResult setScooterModel(SetModelEnter enter);

    /**
     * 注册时输入邮箱校验是否可用
     */
    BooleanResult checkEmail(StringEnter enter);

    /**
     * 注册
     */
    GeneralResult register(AppLoginEnter enter);
}
