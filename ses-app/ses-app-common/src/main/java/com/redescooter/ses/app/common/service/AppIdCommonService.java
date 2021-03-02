package com.redescooter.ses.app.common.service;

import com.redescooter.ses.api.common.vo.base.AppIDResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;

import java.util.List;

/**
 * @Author jerry
 * @Date 2021/2/19 11:57 上午
 * @Description appid 系统应用公共服务
 **/
public interface AppIdCommonService {

    /**
     * 枚举转对象给前端
     *
     * @param enter
     * @return
     */
    List<AppIDResult> list(GeneralEnter enter);
}
