package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeWmsStockRecord;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 *@author assert
 *@date 2021/1/14 15:45
 */
public interface OpeWmsStockRecordMapper extends BaseMapper {
    int updateBatch(List<OpeWmsStockRecord> list);

    int batchInsert(@Param("list") List<OpeWmsStockRecord> list);

    int insertOrUpdate(OpeWmsStockRecord record);

    int insertOrUpdateSelective(OpeWmsStockRecord record);
}