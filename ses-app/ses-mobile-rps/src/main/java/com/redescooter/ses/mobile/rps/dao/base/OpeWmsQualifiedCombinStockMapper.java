package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeWmsQualifiedCombinStock;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 *@author assert
 *@date 2021/1/14 15:44
 */
public interface OpeWmsQualifiedCombinStockMapper extends BaseMapper {
    int updateBatch(List<OpeWmsQualifiedCombinStock> list);

    int batchInsert(@Param("list") List<OpeWmsQualifiedCombinStock> list);

    int insertOrUpdate(OpeWmsQualifiedCombinStock record);

    int insertOrUpdateSelective(OpeWmsQualifiedCombinStock record);
}