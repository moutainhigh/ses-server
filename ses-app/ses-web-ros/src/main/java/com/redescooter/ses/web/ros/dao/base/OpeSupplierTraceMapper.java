package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeSupplierTrace;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OpeSupplierTraceMapper extends BaseMapper<OpeSupplierTrace> {
    int updateBatch(List<OpeSupplierTrace> list);

    int batchInsert(@Param("list") List<OpeSupplierTrace> list);

    int insertOrUpdate(OpeSupplierTrace record);

    int insertOrUpdateSelective(OpeSupplierTrace record);
}