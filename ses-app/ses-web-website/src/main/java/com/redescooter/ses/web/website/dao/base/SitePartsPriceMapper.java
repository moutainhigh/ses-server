package com.redescooter.ses.web.website.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.website.dm.SitePartsPrice;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SitePartsPriceMapper extends BaseMapper<SitePartsPrice> {
    int updateBatch(List<SitePartsPrice> list);

    int updateBatchSelective(List<SitePartsPrice> list);

    int batchInsert(@Param("list") List<SitePartsPrice> list);

    int insertOrUpdate(SitePartsPrice record);

    int insertOrUpdateSelective(SitePartsPrice record);
}