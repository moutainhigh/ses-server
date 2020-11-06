package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeEntrustCombinB;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeEntrustCombinBMapper extends BaseMapper<OpeEntrustCombinB> {
    int updateBatch(List<OpeEntrustCombinB> list);

    int batchInsert(@Param("list") List<OpeEntrustCombinB> list);

    int insertOrUpdate(OpeEntrustCombinB record);

    int insertOrUpdateSelective(OpeEntrustCombinB record);
}