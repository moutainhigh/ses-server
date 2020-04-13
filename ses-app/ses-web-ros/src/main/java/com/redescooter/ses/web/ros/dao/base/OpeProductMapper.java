package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeProduct;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OpeProductMapper extends BaseMapper<OpeProduct> {
    int updateBatch(List<OpeProduct> list);

    int batchInsert(@Param("list") List<OpeProduct> list);

    int insertOrUpdate(OpeProduct record);

    int insertOrUpdateSelective(OpeProduct record);
}