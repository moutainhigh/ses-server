package com.redescooter.ses.web.ros.service.wms;

import com.redescooter.ses.api.common.vo.CommonNodeResult;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.vo.wms.StartWhOutOrderEnter;
import com.redescooter.ses.web.ros.vo.wms.WhOutDetailProductPartListEnter;
import com.redescooter.ses.web.ros.vo.wms.WhOutDetailProductPartListResult;
import com.redescooter.ses.web.ros.vo.wms.WhOutDetailResult;
import com.redescooter.ses.web.ros.vo.wms.WhOutListEnter;
import com.redescooter.ses.web.ros.vo.wms.WhOutListResult;
import com.redescooter.ses.web.ros.vo.wms.WhOutSaveEnter;

/**
 * @ClassName:WhOutService
 * @description: WhOutService
 * @author: Alex
 * @Version：1.3
 * @create: 2020/07/20 10:29
 */
public interface WhOutService {
    /**
     * 出库单列表
     *
     * @param enter
     * @return
     */
    PageResult<WhOutListResult> whList(WhOutListEnter enter);

    /**
     * 详情
     *
     * @param enter
     * @return
     */
    WhOutDetailResult detail(IdEnter enter);

    /**
     * 订单节点
     *
     * @param enter
     * @return
     */
    CommonNodeResult nodeDetail(IdEnter enter);

    /**
     * 详情产品列表
     *
     * @param enter
     * @return
     */
    PageResult<WhOutDetailProductPartListResult> detailProductPartList(WhOutDetailProductPartListEnter enter);

    /**
     * 取消出库单
     *
     * @param enter
     * @return
     */
    GeneralResult cancel(IdEnter enter);

    /**
     * 开始出库单
     *
     * @param enter
     * @return
     */
    GeneralResult start(StartWhOutOrderEnter enter);

    /**
     * 确认备料
     * @param enter
     * @return
     */
    GeneralResult prepareMaterial(IdEnter enter);

    /**
     * 入库
     * @param enter
     * @return
     */
    GeneralResult inWh(IdEnter enter);

    /**
     * 保存
     * @param enter
     * @return
     */
    GeneralResult save(WhOutSaveEnter enter);

    //收货人单列表
    //仓库列表
    //发货方式
    //产品列表

}

