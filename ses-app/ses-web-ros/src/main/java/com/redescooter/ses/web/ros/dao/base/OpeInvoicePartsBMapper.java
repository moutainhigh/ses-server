package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeInvoicePartsB;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OpeInvoicePartsBMapper extends BaseMapper<OpeInvoicePartsB> {
    int updateBatch(List<OpeInvoicePartsB> list);

    int batchInsert(@Param("list") List<OpeInvoicePartsB> list);

    int insertOrUpdate(OpeInvoicePartsB record);

    int insertOrUpdateSelective(OpeInvoicePartsB record);
}