package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeStockBills;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OpeStockBillsMapper extends BaseMapper<OpeStockBills> {
    int updateBatch(List<OpeStockBills> list);

    int batchInsert(@Param("list") List<OpeStockBills> list);

    int insertOrUpdate(OpeStockBills record);

    int insertOrUpdateSelective(OpeStockBills record);
}