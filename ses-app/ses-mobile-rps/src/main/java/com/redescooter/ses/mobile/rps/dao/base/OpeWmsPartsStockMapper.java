package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeWmsPartsStock;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 *@author assert
 *@date 2021/1/14 15:44
 */
public interface OpeWmsPartsStockMapper extends BaseMapper {
    int updateBatch(List<OpeWmsPartsStock> list);

    int batchInsert(@Param("list") List<OpeWmsPartsStock> list);

    int insertOrUpdate(OpeWmsPartsStock record);

    int insertOrUpdateSelective(OpeWmsPartsStock record);
}