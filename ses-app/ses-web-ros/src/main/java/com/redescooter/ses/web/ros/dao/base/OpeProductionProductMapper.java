package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeProductionProduct;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OpeProductionProductMapper extends BaseMapper<OpeProductionProduct> {
    int updateBatch(List<OpeProductionProduct> list);

    int batchInsert(@Param("list") List<OpeProductionProduct> list);

    int insertOrUpdate(OpeProductionProduct record);

    int insertOrUpdateSelective(OpeProductionProduct record);
}