package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeEntrustPartsB;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeEntrustPartsBMapper extends BaseMapper<OpeEntrustPartsB> {
    int updateBatch(List<OpeEntrustPartsB> list);

    int batchInsert(@Param("list") List<OpeEntrustPartsB> list);

    int insertOrUpdate(OpeEntrustPartsB record);

    int insertOrUpdateSelective(OpeEntrustPartsB record);
}