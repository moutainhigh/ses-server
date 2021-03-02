package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeInvoiceCombinB;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OpeInvoiceCombinBMapper extends BaseMapper<OpeInvoiceCombinB> {
    int updateBatch(List<OpeInvoiceCombinB> list);

    int batchInsert(@Param("list") List<OpeInvoiceCombinB> list);

    int insertOrUpdate(OpeInvoiceCombinB record);

    int insertOrUpdateSelective(OpeInvoiceCombinB record);
}