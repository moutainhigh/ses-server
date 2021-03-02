package com.redescooter.ses.web.website.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.website.dm.SiteProduct;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SiteProductMapper extends BaseMapper<SiteProduct> {
    int updateBatch(List<SiteProduct> list);

    int updateBatchSelective(List<SiteProduct> list);

    int batchInsert(@Param("list") List<SiteProduct> list);

    int insertOrUpdate(SiteProduct record);

    int insertOrUpdateSelective(SiteProduct record);
}