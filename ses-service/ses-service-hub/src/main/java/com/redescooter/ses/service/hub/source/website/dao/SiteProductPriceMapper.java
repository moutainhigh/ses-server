package com.redescooter.ses.service.hub.source.website.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.service.hub.source.website.dm.SiteProductPrice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SiteProductPriceMapper extends BaseMapper<SiteProductPrice> {
    int updateBatch(List<SiteProductPrice> list);

    int updateBatchSelective(List<SiteProductPrice> list);

    int batchInsert(@Param("list") List<SiteProductPrice> list);

    int insertOrUpdate(SiteProductPrice record);

    int insertOrUpdateSelective(SiteProductPrice record);
}