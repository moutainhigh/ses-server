package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeWmsScooterStock;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 *@author assert
 *@date 2021/1/14 15:44
 */
public interface OpeWmsScooterStockMapper extends BaseMapper<OpeWmsScooterStock> {
    int updateBatch(List<OpeWmsScooterStock> list);

    int batchInsert(@Param("list") List<OpeWmsScooterStock> list);

    int insertOrUpdate(OpeWmsScooterStock record);

    int insertOrUpdateSelective(OpeWmsScooterStock record);
}
