package com.redescooter.ses.web.website.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.website.dm.SiteOrder;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SiteOrderMapper extends BaseMapper<SiteOrder> {
    int updateBatch(List<SiteOrder> list);

    int updateBatchSelective(List<SiteOrder> list);

    int batchInsert(@Param("list") List<SiteOrder> list);

    int insertOrUpdate(SiteOrder record);

    int insertOrUpdateSelective(SiteOrder record);
}