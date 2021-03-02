package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeWmsQualifiedScooterStock;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author assert
 * @date 2021/1/29 11:52
 */
public interface OpeWmsQualifiedScooterStockMapper extends BaseMapper<OpeWmsQualifiedScooterStock> {
    int updateBatch(List<OpeWmsQualifiedScooterStock> list);

    int batchInsert(@Param("list") List<OpeWmsQualifiedScooterStock> list);

    int insertOrUpdate(OpeWmsQualifiedScooterStock record);

    int insertOrUpdateSelective(OpeWmsQualifiedScooterStock record);
}