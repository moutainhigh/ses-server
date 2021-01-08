package com.redescooter.ses.web.website.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.website.dm.SiteParts;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SitePartsMapper extends BaseMapper<SiteParts> {
    int updateBatch(List<SiteParts> list);

    int updateBatchSelective(List<SiteParts> list);

    int batchInsert(@Param("list") List<SiteParts> list);

    int insertOrUpdate(SiteParts record);

    int insertOrUpdateSelective(SiteParts record);
}