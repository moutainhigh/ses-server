package com.redescooter.ses.web.website.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.website.dm.SiteOrderB;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SiteOrderBMapper extends BaseMapper<SiteOrderB> {
    int updateBatch(List<SiteOrderB> list);

    int updateBatchSelective(List<SiteOrderB> list);

    int batchInsert(@Param("list") List<SiteOrderB> list);

    int insertOrUpdate(SiteOrderB record);

    int insertOrUpdateSelective(SiteOrderB record);
}