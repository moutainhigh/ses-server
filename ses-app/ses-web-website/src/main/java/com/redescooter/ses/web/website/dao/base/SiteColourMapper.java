package com.redescooter.ses.web.website.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.website.dm.SiteColour;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SiteColourMapper extends BaseMapper<SiteColour> {
    int updateBatch(List<SiteColour> list);

    int updateBatchSelective(List<SiteColour> list);

    int batchInsert(@Param("list") List<SiteColour> list);

    int insertOrUpdate(SiteColour record);

    int insertOrUpdateSelective(SiteColour record);
}