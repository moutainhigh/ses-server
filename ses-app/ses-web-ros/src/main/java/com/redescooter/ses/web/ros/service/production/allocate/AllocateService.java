package com.redescooter.ses.web.ros.service.production.allocate;

import com.redescooter.ses.api.common.vo.SaveNodeEnter;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.vo.production.ConsigneeResult;
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
 * @ClassName:AllocateService
 * @description: AllocateService
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/19 15:10
 */
public interface AllocateService {
    /**
     * 类型统计
     *
     * @return
     */
    Map<String, Integer> countByType(GeneralEnter enter);

    /**
     * 状态列表
     *
     * @param enter
     * @return
     */
    Map<String, String> statusList(GeneralEnter enter);

    /**
     * 调拨单列表
     *
     * @param enter
     * @return
     */
    PageResult<AllocateOrderResult> list(AllocateOrderEnter enter);

    /**
     * 详情
     *
     * @param enter
     * @return
     */
    AllocateOrderResult detail(IdEnter enter);

    /**
     * 调拨单节点
     *
     * @param enter
     * @return
     */
    List<AllocateOrderNodeResult> allocateOrderNode(IdEnter enter);

    /**
     * 调拨单部件列表
     *
     * @param enter
     * @return
     */
    List<AllocateOrderPartResult> allocateOrderDetailPartsList(IdEnter enter);

    /**
     * 备料
     *
     * @param enter
     * @return
     */
    GeneralResult startPrepare(IdEnter enter);

    /**
     * 开始调拨单
     *
     * @param enter
     * @return
     */
    GeneralResult startAllocate(IdEnter enter);

    /**
     * 取消调拨单
     *
     * @param enter
     * @return
     */
    GeneralResult cancelAllocate(IdEnter enter);

    /**
     * 部件列表
     *
     * @param enter
     * @return
     */
    List<ProductPartsResult> allocatePartsList(ProductPartsListEnter enter);

    /**
     * 入库调拨单
     *
     * @param enter
     * @return
     */
    GeneralResult inWhAllocate(IdEnter enter);

    /**
     * 保存调拨单
     *
     * @param enter
     * @return
     */
    GeneralResult saveAllocate(SaveAllocateEnter enter);

    /**
     * 收件人列表
     *
     * @param enter
     * @return
     */
    List<ConsigneeResult> consigneeList(GeneralEnter enter);

    /**
     * 保存节点
     *
     * @return
     */
    GeneralResult saveAllocateNode(SaveNodeEnter enter);
}
