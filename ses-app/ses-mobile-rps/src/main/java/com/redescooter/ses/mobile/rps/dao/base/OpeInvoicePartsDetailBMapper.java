package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeInvoicePartsDetailB;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OpeInvoicePartsDetailBMapper extends BaseMapper<OpeInvoicePartsDetailB> {
    int updateBatch(List<OpeInvoicePartsDetailB> list);

    int batchInsert(@Param("list") List<OpeInvoicePartsDetailB> list);

    int insertOrUpdate(OpeInvoicePartsDetailB record);

    int insertOrUpdateSelective(OpeInvoicePartsDetailB record);
}