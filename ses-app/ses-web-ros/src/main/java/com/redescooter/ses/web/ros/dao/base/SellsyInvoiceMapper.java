package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.SellsyInvoice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SellsyInvoiceMapper extends BaseMapper<SellsyInvoice> {
    int updateBatch(List<SellsyInvoice> list);

    int updateBatchSelective(List<SellsyInvoice> list);

    int batchInsert(@Param("list") List<SellsyInvoice> list);

    int insertOrUpdate(SellsyInvoice record);

    int insertOrUpdateSelective(SellsyInvoice record);
}