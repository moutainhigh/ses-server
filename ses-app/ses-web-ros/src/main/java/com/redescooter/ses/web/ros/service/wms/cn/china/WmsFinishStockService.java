package com.redescooter.ses.web.ros.service.wms.cn.china;

import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.vo.wms.cn.china.WmsFinishScooterListEnter;
import com.redescooter.ses.web.ros.vo.wms.cn.china.WmsFinishScooterListResult;
import com.redescooter.ses.web.ros.vo.wms.cn.china.WmsfinishScooterDetailResult;

/**
 * @description: 成品库服务层
 * @author: Aleks
 * @create: 2020/12/28 15:00
 */
public interface WmsFinishStockService {


    /**
     * 成品库车辆库存列表
     * @param enter
     * @return
     */
    PageResult<WmsFinishScooterListResult> finishScooterList(WmsFinishScooterListEnter enter);


    /**
     * 成品库车辆库存详情
     * @param enter
     * @return
     */
    WmsfinishScooterDetailResult finishScooterDetail(IdEnter enter);

}
