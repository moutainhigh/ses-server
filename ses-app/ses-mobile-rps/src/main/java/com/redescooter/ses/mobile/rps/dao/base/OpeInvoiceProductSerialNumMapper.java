package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeInvoiceProductSerialNum;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeInvoiceProductSerialNumMapper extends BaseMapper<OpeInvoiceProductSerialNum> {
    int updateBatch(List<OpeInvoiceProductSerialNum> list);

    int batchInsert(@Param("list") List<OpeInvoiceProductSerialNum> list);

    int insertOrUpdate(OpeInvoiceProductSerialNum record);

    int insertOrUpdateSelective(OpeInvoiceProductSerialNum record);
}