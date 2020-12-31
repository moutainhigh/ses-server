package com.redescooter.ses.web.ros.service.wms.cn.fr;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.ros.vo.wms.cn.fr.FrStockCountResult;
import com.redescooter.ses.web.ros.vo.wms.cn.fr.FrTodayInOrOutStockCountResult;

import java.util.List;

/**
 * @description:
 * @author: Aleks
 * @create: 2020/12/31 11:56
 */
public interface FrWhService {

    /**
     *  出入库统计
     * @param enter
     * @return
     */
    List<FrTodayInOrOutStockCountResult> inOrOutCount(GeneralEnter enter);


    /**
     *  库存统计
     * @param enter
     * @return
     */
    FrStockCountResult stockCount(GeneralEnter enter);


}
