package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeWmsPartsStock;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author assert
 * @date 2021/2/2 17:15
 */
public interface OpeWmsPartsStockMapper extends BaseMapper<OpeWmsPartsStock> {
    int updateBatch(List<OpeWmsPartsStock> list);

    int updateBatchSelective(List<OpeWmsPartsStock> list);

    int batchInsert(@Param("list") List<OpeWmsPartsStock> list);

    int insertOrUpdate(OpeWmsPartsStock record);

    int insertOrUpdateSelective(OpeWmsPartsStock record);
}