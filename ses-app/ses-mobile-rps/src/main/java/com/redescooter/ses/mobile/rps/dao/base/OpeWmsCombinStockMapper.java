package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeWmsCombinStock;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author assert
 * @date 2021/2/2 17:13
 */
public interface OpeWmsCombinStockMapper extends BaseMapper<OpeWmsCombinStock> {
    int updateBatch(List<OpeWmsCombinStock> list);

    int updateBatchSelective(List<OpeWmsCombinStock> list);

    int batchInsert(@Param("list") List<OpeWmsCombinStock> list);

    int insertOrUpdate(OpeWmsCombinStock record);

    int insertOrUpdateSelective(OpeWmsCombinStock record);
}