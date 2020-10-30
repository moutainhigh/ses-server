package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeInvoiceOrder;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OpeInvoiceOrderMapper extends BaseMapper<OpeInvoiceOrder> {
    int updateBatch(List<OpeInvoiceOrder> list);

    int batchInsert(@Param("list") List<OpeInvoiceOrder> list);

    int insertOrUpdate(OpeInvoiceOrder record);

    int insertOrUpdateSelective(OpeInvoiceOrder record);
}