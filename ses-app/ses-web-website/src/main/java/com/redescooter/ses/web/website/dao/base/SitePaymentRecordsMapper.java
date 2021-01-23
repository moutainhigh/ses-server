package com.redescooter.ses.web.website.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.website.dm.SitePaymentRecords;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SitePaymentRecordsMapper extends BaseMapper<SitePaymentRecords> {
    int updateBatch(List<SitePaymentRecords> list);

    int updateBatchSelective(List<SitePaymentRecords> list);

    int batchInsert(@Param("list") List<SitePaymentRecords> list);

    int insertOrUpdate(SitePaymentRecords record);

    int insertOrUpdateSelective(SitePaymentRecords record);
}