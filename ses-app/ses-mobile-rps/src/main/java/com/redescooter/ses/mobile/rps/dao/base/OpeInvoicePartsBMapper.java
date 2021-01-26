package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeInvoicePartsB;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OpeInvoicePartsBMapper extends BaseMapper<OpeInvoicePartsB> {
    int updateBatch(List<OpeInvoicePartsB> list);

    int batchInsert(@Param("list") List<OpeInvoicePartsB> list);

    int insertOrUpdate(OpeInvoicePartsB record);

    int insertOrUpdateSelective(OpeInvoicePartsB record);
}