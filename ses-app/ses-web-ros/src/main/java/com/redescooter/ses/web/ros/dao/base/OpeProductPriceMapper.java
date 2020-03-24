package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeProductPrice;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OpeProductPriceMapper extends BaseMapper<OpeProductPrice> {
    int updateBatch(List<OpeProductPrice> list);

    int batchInsert(@Param("list") List<OpeProductPrice> list);

    int insertOrUpdate(OpeProductPrice record);

    int insertOrUpdateSelective(OpeProductPrice record);
}