package com.redescooter.ses.web.ros.service.wthdrawalsite;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.ros.vo.wthdrawalsite.WthdrawalSiteEditEnter;
import com.redescooter.ses.web.ros.vo.wthdrawalsite.WthdrawalSiteResult;
import com.redescooter.ses.web.ros.vo.wthdrawalsite.WthdrawalSiteSaveEnter;
import com.redescooter.ses.web.ros.vo.wthdrawalsite.isSwitchEnter;

import java.util.List;

public interface WthdrawalSiteServer {

    /**
     * 保存门店信息
     *
     * @param enter
     * @return
     */
    GeneralResult save(WthdrawalSiteSaveEnter enter);

    /**
     * 门店编辑
     *
     * @param enter
     * @param id
     * @return
     */
    GeneralResult edit(WthdrawalSiteEditEnter enter);

    /**
     * 门店详情
     *
     * @param enter
     * @param id
     * @return
     */
    WthdrawalSiteResult details(IdEnter enter);

    /**
     * 门店列表
     *
     * @param enter
     * @return
     */
    List<WthdrawalSiteResult> list(GeneralEnter enter);

    /**
     * 营业开关
     *
     * @param enter
     * @param id
     * @return
     */
    GeneralResult isSwitch(isSwitchEnter enter);
}
