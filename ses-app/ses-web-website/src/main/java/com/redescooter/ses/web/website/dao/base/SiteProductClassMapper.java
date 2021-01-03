package com.redescooter.ses.web.website.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.website.dm.SiteProductClass;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SiteProductClassMapper extends BaseMapper<SiteProductClass> {
    int updateBatch(List<SiteProductClass> list);

    int updateBatchSelective(List<SiteProductClass> list);

    int batchInsert(@Param("list") List<SiteProductClass> list);

    int insertOrUpdate(SiteProductClass record);

    int insertOrUpdateSelective(SiteProductClass record);
}