package com.redescooter.ses.web.ros.service.wms.cn.china;

import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.vo.wms.cn.china.MaterialStockPartsListEnter;
import com.redescooter.ses.web.ros.vo.wms.cn.china.MaterialStockPartsListResult;
import com.redescooter.ses.web.ros.vo.wms.cn.china.MaterialpartsStockDetailResult;

/**
 * @description:原料库服务层
 * @author: Aleks
 * @create: 2020/12/28 15:06
 */
public interface WmsMaterialStockService {

    /**
     * 原料库部件库存列表
     * @param enter
     * @return
     */
    PageResult<MaterialStockPartsListResult> materialStockPartsList(MaterialStockPartsListEnter enter);


    /**
     * 原料库部件详情
     * @param enter
     * @return
     */
    MaterialpartsStockDetailResult materialStockPartsDetail(IdEnter enter);


    /**
     * 待入库数量增加（入库单变成待入库状态的时候调用）
     * @param productionType 1:scooter 2:combin 3:parts
     * @param id
     * @param stockType 1:中国仓库 2：法国仓库
     * @param userId 操作人ID
     */
    void waitInStock(Integer productionType,Long id,Integer stockType,Long userId);


    /**
     * 已入库数量增加 待入库数量减少 (入库单入库的时候调用)
     * @param productionType 1:scooter 2:combin 3:parts
     * @param id
     * @param stockType 1:中国仓库 2：法国仓库
     * @param userId 操作人ID
     * @param inWhType 入库类型，1：生产入库，2：返修入库，3：采购入库，5：退料入库，6：其他
     */
    void inStock(Integer productionType,Long id,Integer stockType,Long userId,Integer inWhType);


    /**
     * 可用库存减少  待出库库存增加
     * @param productionType
     * @param id
     * @param stockType
     * @param userId
     * @param inWhType 入库类型，1：生产入库，2：返修入库，3：采购入库，5：退料入库，6：其他
     */
//    void ableLowWaitOutUp(Integer productionType,Long id,Integer stockType,Long userId,Integer inWhType);

    /**
     * 待出库库存减少 （用于出库单取消的时候调用）
     * @param productionType
     * @param id
     * @param stockType
     * @param userId
     * @param inWhType 入库类型，1：生产入库，2：返修入库，3：采购入库，5：退料入库，6：其他
     */
    void waitOutLow(Integer productionType,Long id,Integer stockType,Long userId,Integer inWhType);


    /**
     * 已用库存增加 可用库存减少 待出库库存减少(出库单出库的时候调用)
     * @param productionType
     * @param id
     * @param stockType
     * @param userId
     * @param inWhType 入库类型，1：生产入库，2：返修入库，3：采购入库，5：退料入库，6：其他
     */
    void usedlUpWaitOutLow(Integer productionType,Long id,Integer stockType,Long userId,Integer inWhType);


    /**
     * 待出库的库存增加 （用于产生出库单的时候）
     * @param productionType
     * @param id
     * @param stockType
     * @param userId
     * @param inWhType 入库类型，1：生产入库，2：返修入库，3：采购入库，5：退料入库，6：其他
     */
    void waitOutUp(Integer productionType,Long id,Integer stockType,Long userId,Integer inWhType);


    /**
     * 待出库数量减少 可用库存减少  已用库存增加 （用于出库单出库）
     * @param productionType
     * @param id
     * @param stockType
     * @param userId
     * @param inWhType 入库类型，1：生产入库，2：返修入库，3：采购入库，5：退料入库，6：其他
     */
    void waitOutLowAbleLowUsedUp(Integer productionType,Long id,Integer stockType,Long userId,Integer inWhType);


    /**
     * 法国仓库待入库数量增加（委托单发货的时候调用）
     * @param productionType 1:scooter 2:combin 3:parts
     * @param entrustId
     * @param stockType 1:中国仓库 2：法国仓库
     * @param userId 操作人ID
     */
    void FrWaitInStock(Integer productionType,Long entrustId,Integer stockType,Long userId);


    /**
     * 法国仓库已入库数量增加 待入库数量减少 (委托单签收的时候调用)
     * @param productionType 1:scooter 2:combin 3:parts
     * @param entrustId
     * @param stockType 1:中国仓库 2：法国仓库
     * @param userId 操作人ID
     * @param inWhType 入库类型，1：生产入库，2：返修入库，3：采购入库，5：退料入库，6：其他
     */
    void frInStock(Integer productionType,Long entrustId,Integer stockType,Long userId,Integer inWhType);
}
