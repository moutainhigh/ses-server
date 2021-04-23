package com.redescooter.ses.web.ros.service.wms.cn.fr;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.ros.vo.wms.cn.fr.FrInWhOrderAddEnter;

/**
 * @Description 法国仓库入库单Service层
 * @Author Chris
 * @Date 2021/4/15 10:07
 */
public interface FrInWhOrderService {

    /**
     * 新增法国仓库入库单
     */
    GeneralResult addFrInWhOrder(FrInWhOrderAddEnter enter);

    /**
     * 确认入库
     */
    GeneralResult confirmInWh(IdEnter enter);

}
