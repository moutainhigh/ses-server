package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.SellsyInvoice;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface SellsyInvoiceMapper extends BaseMapper<SellsyInvoice> {
    int updateBatch(List<SellsyInvoice> list);

    int batchInsert(@Param("list") List<SellsyInvoice> list);

    int insertOrUpdate(SellsyInvoice record);

    int insertOrUpdateSelective(SellsyInvoice record);

    int updateBatchSelective(List<SellsyInvoice> list);
}