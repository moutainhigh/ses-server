package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeInvoiceScooterB;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OpeInvoiceScooterBMapper extends BaseMapper<OpeInvoiceScooterB> {
    int updateBatch(List<OpeInvoiceScooterB> list);

    int batchInsert(@Param("list") List<OpeInvoiceScooterB> list);

    int insertOrUpdate(OpeInvoiceScooterB record);

    int insertOrUpdateSelective(OpeInvoiceScooterB record);
}