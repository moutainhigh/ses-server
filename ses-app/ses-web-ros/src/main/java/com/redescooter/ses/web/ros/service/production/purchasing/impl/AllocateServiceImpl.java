package com.redescooter.ses.web.ros.service.production.purchasing.impl;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.service.production.purchasing.AllocateService;
import com.redescooter.ses.web.ros.vo.production.ProductPartsListEnter;
import com.redescooter.ses.web.ros.vo.production.ProductPartsResult;
import com.redescooter.ses.web.ros.vo.production.allocate.AllocateOrderEnter;
import com.redescooter.ses.web.ros.vo.production.allocate.AllocateOrderNodeResult;
import com.redescooter.ses.web.ros.vo.production.allocate.AllocateOrderPartResult;
import com.redescooter.ses.web.ros.vo.production.allocate.AllocateOrderResult;
import com.redescooter.ses.web.ros.vo.production.allocate.SaveAllocateEnter;

import java.util.List;
import java.util.Map;

/**
 * @ClassName:AllocateServiceImpl
 * @description: AllocateServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/19 15:10
 */
public class AllocateServiceImpl implements AllocateService {

    /**
     * 类型统计
     *
     * @return
     */
    @Override
    public Map<String, Integer> countByType() {
        return null;
    }

    /**
     * 调拨单列表
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<AllocateOrderResult> list(AllocateOrderEnter enter) {
        return null;
    }

    /**
     * 详情
     *
     * @param enter
     * @return
     */
    @Override
    public AllocateOrderResult detail(IdEnter enter) {
        return null;
    }

    /**
     * 调拨单节点
     *
     * @param enter
     * @return
     */
    @Override
    public List<AllocateOrderNodeResult> allocateOrderNode(IdEnter enter) {
        return null;
    }

    /**
     * 调拨单部件列表
     *
     * @param enter
     * @return
     */
    @Override
    public List<AllocateOrderPartResult> allocateOrderPartsList(IdEnter enter) {
        return null;
    }

    /**
     * 开始调拨单
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult startAllocate(IdEnter enter) {
        return null;
    }

    /**
     * 取消调拨单
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult cancelAllocate(IdEnter enter) {
        return null;
    }

    /**
     * 部件列表
     *
     * @param enter
     * @return
     */
    @Override
    public ProductPartsResult partsList(ProductPartsListEnter enter) {
        return null;
    }

    /**
     * 入库调拨单
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult inWhAllocate(IdEnter enter) {
        return null;
    }

    /**
     * 保存调拨单
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult saveAllocate(SaveAllocateEnter enter) {
        return null;
    }
}
