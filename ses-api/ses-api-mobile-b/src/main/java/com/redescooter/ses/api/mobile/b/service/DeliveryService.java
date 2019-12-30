package com.redescooter.ses.api.mobile.b.service;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.mobile.b.vo.CompleteEnter;
import com.redescooter.ses.api.mobile.b.vo.CompleteResult;
import com.redescooter.ses.api.mobile.b.vo.DeliveryDetailResult;
import com.redescooter.ses.api.mobile.b.vo.DeliveryListResult;
import com.redescooter.ses.api.mobile.b.vo.RefuseEnter;
import com.redescooter.ses.api.mobile.b.vo.StartEnter;

/**
 * @ClassName:DeliveryService
 * @description: DeliveryService
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/29 14:29
 */
public interface DeliveryService {
    /**
     * delviery 列表
     *
     * @param enter
     * @return
     */
    DeliveryListResult list(GeneralEnter enter);

    /**
     * detail
     *
     * @param enter
     * @return
     */
    DeliveryDetailResult detail(IdEnter enter);

    /**
     * 开始
     *
     * @param enter
     * @return
     */
    GeneralResult start(StartEnter enter);

    /**
     * 拒绝
     *
     * @param enter
     * @return
     */
    GeneralResult refuse(RefuseEnter enter);

    /**
     * 完成
     *
     * @param enter
     * @return
     */
    CompleteResult complete(CompleteEnter enter);
}
