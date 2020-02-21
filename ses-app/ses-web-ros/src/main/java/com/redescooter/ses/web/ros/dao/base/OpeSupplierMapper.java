package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeSupplier;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OpeSupplierMapper extends BaseMapper<OpeSupplier> {
    int updateBatch(List<OpeSupplier> list);

    int batchInsert(@Param("list") List<OpeSupplier> list);

    int insertOrUpdate(OpeSupplier record);

    int insertOrUpdateSelective(OpeSupplier record);
}