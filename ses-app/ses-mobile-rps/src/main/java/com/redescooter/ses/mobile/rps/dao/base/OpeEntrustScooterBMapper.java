package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeEntrustScooterB;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeEntrustScooterBMapper extends BaseMapper<OpeEntrustScooterB> {
    int updateBatch(List<OpeEntrustScooterB> list);

    int batchInsert(@Param("list") List<OpeEntrustScooterB> list);

    int insertOrUpdate(OpeEntrustScooterB record);

    int insertOrUpdateSelective(OpeEntrustScooterB record);
}