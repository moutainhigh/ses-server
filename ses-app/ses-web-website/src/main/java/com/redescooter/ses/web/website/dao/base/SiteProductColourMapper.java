package com.redescooter.ses.web.website.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.website.dm.SiteProductColour;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SiteProductColourMapper extends BaseMapper<SiteProductColour> {
    int updateBatch(List<SiteProductColour> list);

    int updateBatchSelective(List<SiteProductColour> list);

    int batchInsert(@Param("list") List<SiteProductColour> list);

    int insertOrUpdate(SiteProductColour record);

    int insertOrUpdateSelective(SiteProductColour record);
}