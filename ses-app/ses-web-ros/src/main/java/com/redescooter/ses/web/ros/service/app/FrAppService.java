package com.redescooter.ses.web.ros.service.app;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.TokenResult;
import com.redescooter.ses.web.ros.dm.OpeWarehouseAccount;
import com.redescooter.ses.web.ros.vo.app.AppLoginEnter;
import com.redescooter.ses.web.ros.vo.app.InquiryDetailResult;
import com.redescooter.ses.web.ros.vo.app.InquiryListAppEnter;
import com.redescooter.ses.web.ros.vo.app.InquiryListResult;

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
    InquiryDetailResult getDetail(IdEnter enter);




}
