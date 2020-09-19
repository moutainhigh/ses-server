package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.SellsyInvoiceTotal;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface SellsyInvoiceTotalMapper extends BaseMapper<SellsyInvoiceTotal> {
    int updateBatch(List<SellsyInvoiceTotal> list);

    int batchInsert(@Param("list") List<SellsyInvoiceTotal> list);

    int insertOrUpdate(SellsyInvoiceTotal record);

    int insertOrUpdateSelective(SellsyInvoiceTotal record);
}