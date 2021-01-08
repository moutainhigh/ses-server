package com.redescooter.ses.web.website.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.website.dm.SiteContactUsTrace;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SiteContactUsTraceMapper extends BaseMapper<SiteContactUsTrace> {
    int updateBatch(List<SiteContactUsTrace> list);

    int updateBatchSelective(List<SiteContactUsTrace> list);

    int batchInsert(@Param("list") List<SiteContactUsTrace> list);

    int insertOrUpdate(SiteContactUsTrace record);

    int insertOrUpdateSelective(SiteContactUsTrace record);
}