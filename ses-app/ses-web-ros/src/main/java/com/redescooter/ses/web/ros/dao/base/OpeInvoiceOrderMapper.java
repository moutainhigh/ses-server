package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeInvoiceOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OpeInvoiceOrderMapper extends BaseMapper<OpeInvoiceOrder> {
    int updateBatch(List<OpeInvoiceOrder> list);

    int batchInsert(@Param("list") List<OpeInvoiceOrder> list);

    int insertOrUpdate(OpeInvoiceOrder record);

    int insertOrUpdateSelective(OpeInvoiceOrder record);
}