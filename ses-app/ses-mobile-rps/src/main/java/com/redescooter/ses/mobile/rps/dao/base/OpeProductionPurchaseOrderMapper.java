package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeProductionPurchaseOrder;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author assert
 * @date 2021/2/2 11:34
 */
public interface OpeProductionPurchaseOrderMapper extends BaseMapper<OpeProductionPurchaseOrder> {
    int updateBatch(List<OpeProductionPurchaseOrder> list);

    int updateBatchSelective(List<OpeProductionPurchaseOrder> list);

    int batchInsert(@Param("list") List<OpeProductionPurchaseOrder> list);

    int insertOrUpdate(OpeProductionPurchaseOrder record);

    int insertOrUpdateSelective(OpeProductionPurchaseOrder record);
}