package com.redescooter.ses.web.website.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.website.dm.SiteCustomer;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SiteCustomerMapper extends BaseMapper<SiteCustomer> {
    int updateBatch(List<SiteCustomer> list);

    int updateBatchSelective(List<SiteCustomer> list);

    int batchInsert(@Param("list") List<SiteCustomer> list);

    int insertOrUpdate(SiteCustomer record);

    int insertOrUpdateSelective(SiteCustomer record);
}