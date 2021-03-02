package com.redescooter.ses.web.website.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.website.dm.SiteUser;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SiteUserMapper extends BaseMapper<SiteUser> {
    int updateBatch(List<SiteUser> list);

    int updateBatchSelective(List<SiteUser> list);

    int batchInsert(@Param("list") List<SiteUser> list);

    int insertOrUpdate(SiteUser record);

    int insertOrUpdateSelective(SiteUser record);
}