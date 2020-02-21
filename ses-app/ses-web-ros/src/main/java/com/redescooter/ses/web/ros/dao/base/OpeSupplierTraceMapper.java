package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeSupplierTrace;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OpeSupplierTraceMapper extends BaseMapper<OpeSupplierTrace> {
    int updateBatch(List<OpeSupplierTrace> list);

    int batchInsert(@Param("list") List<OpeSupplierTrace> list);

    int insertOrUpdate(OpeSupplierTrace record);

    int insertOrUpdateSelective(OpeSupplierTrace record);
}