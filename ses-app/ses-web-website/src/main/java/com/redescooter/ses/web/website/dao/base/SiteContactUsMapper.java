package com.redescooter.ses.web.website.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.website.dm.SiteContactUs;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SiteContactUsMapper extends BaseMapper<SiteContactUs> {
    int updateBatch(List<SiteContactUs> list);

    int updateBatchSelective(List<SiteContactUs> list);

    int batchInsert(@Param("list") List<SiteContactUs> list);

    int insertOrUpdate(SiteContactUs record);

    int insertOrUpdateSelective(SiteContactUs record);
}