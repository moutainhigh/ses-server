package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.SellsyInvoiceB;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SellsyInvoiceBMapper extends BaseMapper<SellsyInvoiceB> {
    int updateBatch(List<SellsyInvoiceB> list);

    int updateBatchSelective(List<SellsyInvoiceB> list);

    int batchInsert(@Param("list") List<SellsyInvoiceB> list);

    int insertOrUpdate(SellsyInvoiceB record);

    int insertOrUpdateSelective(SellsyInvoiceB record);
}