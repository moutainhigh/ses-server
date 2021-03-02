package com.redescooter.ses.web.website.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.website.dm.SiteProductParts;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SiteProductPartsMapper extends BaseMapper<SiteProductParts> {
    int updateBatch(List<SiteProductParts> list);

    int updateBatchSelective(List<SiteProductParts> list);

    int batchInsert(@Param("list") List<SiteProductParts> list);

    int insertOrUpdate(SiteProductParts record);

    int insertOrUpdateSelective(SiteProductParts record);
}