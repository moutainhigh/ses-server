package com.redescooter.ses.web.website.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.website.dm.SiteDistributor;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SiteDistributorMapper extends BaseMapper<SiteDistributor> {
    int updateBatch(List<SiteDistributor> list);

    int batchInsert(@Param("list") List<SiteDistributor> list);

    int insertOrUpdate(SiteDistributor record);

    int insertOrUpdateSelective(SiteDistributor record);
}