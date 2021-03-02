package com.redescooter.ses.web.ros.service.wms.cn.china;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.ros.vo.wms.cn.china.ChinaInOrOutCountResult;
import com.redescooter.ses.web.ros.vo.wms.cn.china.StockCountResult;

import java.util.List;

/**
 * @ClassName:ChinaWhService
 * @description: ChinaWhService
 * @author: Alex
 * @Version：1.3
 * @create: 2020/12/27 16:09
 */
public interface ChinaWhService {

    /**
     *  出入库统计
     * @param enter
     * @return
     */
    List<ChinaInOrOutCountResult> inOrOutCount(GeneralEnter enter);


    /**
     *  库存统计
     * @param enter
     * @return
     */
    List<StockCountResult> stockCount(GeneralEnter enter);
}
