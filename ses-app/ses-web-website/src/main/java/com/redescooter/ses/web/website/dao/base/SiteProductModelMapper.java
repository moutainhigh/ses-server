package com.redescooter.ses.web.website.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.website.dm.SiteProductModel;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SiteProductModelMapper extends BaseMapper<SiteProductModel> {
    int updateBatch(List<SiteProductModel> list);

    int updateBatchSelective(List<SiteProductModel> list);

    int batchInsert(@Param("list") List<SiteProductModel> list);

    int insertOrUpdate(SiteProductModel record);

    int insertOrUpdateSelective(SiteProductModel record);
}