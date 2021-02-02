package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeInvoiceScooterDetailB;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OpeInvoiceScooterDetailBMapper extends BaseMapper<OpeInvoiceScooterDetailB> {
    int updateBatch(List<OpeInvoiceScooterDetailB> list);

    int batchInsert(@Param("list") List<OpeInvoiceScooterDetailB> list);

    int insertOrUpdate(OpeInvoiceScooterDetailB record);

    int insertOrUpdateSelective(OpeInvoiceScooterDetailB record);
}