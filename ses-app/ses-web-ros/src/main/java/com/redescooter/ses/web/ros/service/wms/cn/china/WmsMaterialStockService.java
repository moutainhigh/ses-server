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
     * 生成库存的待入库数量
     * @param productionType 1:scooter 2:combin 3:parts
     * @param id
     * @param stockType 1:中国仓库 2：法国仓库
     * @param userId 操作人ID
     */
    void waitInStock(Integer productionType,Long id,Integer stockType,Long userId);


    /**
     * 生成库存的已入库数量
     * @param productionType 1:scooter 2:combin 3:parts
     * @param id
     * @param stockType 1:中国仓库 2：法国仓库
     * @param userId 操作人ID
     * @param inWhType 入库类型，1：生产入库，2：返修入库，3：采购入库，5：退料入库，6：其他
     */
    void inStock(Integer productionType,Long id,Integer stockType,Long userId,Integer inWhType);
}
