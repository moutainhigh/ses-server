package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeWmsStockSerialNumber;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author assert
 * @date 2021/2/2 11:26
 */
public interface OpeWmsStockSerialNumberMapper extends BaseMapper<OpeWmsStockSerialNumber> {
    int updateBatch(List<OpeWmsStockSerialNumber> list);

    int updateBatchSelective(List<OpeWmsStockSerialNumber> list);

    int batchInsert(@Param("list") List<OpeWmsStockSerialNumber> list);

    int insertOrUpdate(OpeWmsStockSerialNumber record);

    int insertOrUpdateSelective(OpeWmsStockSerialNumber record);
}