package com.redescooter.ses.web.website.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.website.dm.SiteProductPrice;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SiteProductPriceMapper extends BaseMapper<SiteProductPrice> {
    int updateBatch(List<SiteProductPrice> list);

    int updateBatchSelective(List<SiteProductPrice> list);

    int batchInsert(@Param("list") List<SiteProductPrice> list);

    int insertOrUpdate(SiteProductPrice record);

    int insertOrUpdateSelective(SiteProductPrice record);
}