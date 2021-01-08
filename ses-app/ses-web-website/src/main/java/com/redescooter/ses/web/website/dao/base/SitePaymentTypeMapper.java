package com.redescooter.ses.web.website.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.website.dm.SitePaymentType;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SitePaymentTypeMapper extends BaseMapper<SitePaymentType> {
    int updateBatch(List<SitePaymentType> list);

    int updateBatchSelective(List<SitePaymentType> list);

    int batchInsert(@Param("list") List<SitePaymentType> list);

    int insertOrUpdate(SitePaymentType record);

    int insertOrUpdateSelective(SitePaymentType record);
}