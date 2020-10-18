package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeWithdrawalSite;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OpeWithdrawalSiteMapper extends BaseMapper<OpeWithdrawalSite> {
    int updateBatch(List<OpeWithdrawalSite> list);

    int updateBatchSelective(List<OpeWithdrawalSite> list);

    int batchInsert(@Param("list") List<OpeWithdrawalSite> list);

    int insertOrUpdate(OpeWithdrawalSite record);

    int insertOrUpdateSelective(OpeWithdrawalSite record);
}