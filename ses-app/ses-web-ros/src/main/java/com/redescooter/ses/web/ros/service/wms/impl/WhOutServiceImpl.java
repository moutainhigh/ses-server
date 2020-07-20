package com.redescooter.ses.web.ros.service.wms.impl;

import com.redescooter.ses.api.common.vo.CommonNodeResult;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.service.wms.WhOutService;
import com.redescooter.ses.web.ros.vo.wms.StartWhOutOrderEnter;
import com.redescooter.ses.web.ros.vo.wms.WhOutDetailProductPartListEnter;
import com.redescooter.ses.web.ros.vo.wms.WhOutDetailProductPartListResult;
import com.redescooter.ses.web.ros.vo.wms.WhOutDetailResult;
import com.redescooter.ses.web.ros.vo.wms.WhOutListEnter;
import com.redescooter.ses.web.ros.vo.wms.WhOutListResult;
import com.redescooter.ses.web.ros.vo.wms.WhOutSaveEnter;

/**
 * @ClassName:WhOutServiceImpl
 * @description: WhOutServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2020/07/20 10:30
 */

public class WhOutServiceImpl implements WhOutService {
    /**
     * 出库单列表
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<WhOutListResult> whList(WhOutListEnter enter) {
        return null;
    }

    /**
     * 详情
     *
     * @param enter
     * @return
     */
    @Override
    public WhOutDetailResult detail(IdEnter enter) {
        return null;
    }

    /**
     * 订单节点
     *
     * @param enter
     * @return
     */
    @Override
    public CommonNodeResult nodeDetail(IdEnter enter) {
        return null;
    }

    /**
     * 详情产品列表
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<WhOutDetailProductPartListResult> detailProductPartList(WhOutDetailProductPartListEnter enter) {
        return null;
    }

    /**
     * 取消出库单
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult cancel(IdEnter enter) {
        return null;
    }

    /**
     * 开始出库单
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult start(StartWhOutOrderEnter enter) {
        return null;
    }

    /**
     * 确认备料
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult prepareMaterial(IdEnter enter) {
        return null;
    }

    /**
     * 入库
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult inWh(IdEnter enter) {
        return null;
    }

    /**
     * 保存
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult save(WhOutSaveEnter enter) {
        return null;
    }
}
