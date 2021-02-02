package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeInvoiceCombinDetailB;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OpeInvoiceCombinDetailBMapper extends BaseMapper<OpeInvoiceCombinDetailB> {
    int updateBatch(List<OpeInvoiceCombinDetailB> list);

    int batchInsert(@Param("list") List<OpeInvoiceCombinDetailB> list);

    int insertOrUpdate(OpeInvoiceCombinDetailB record);

    int insertOrUpdateSelective(OpeInvoiceCombinDetailB record);
}