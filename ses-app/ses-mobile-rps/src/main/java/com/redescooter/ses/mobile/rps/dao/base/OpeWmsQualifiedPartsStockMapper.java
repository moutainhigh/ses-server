package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeWmsQualifiedPartsStock;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 *@author assert
 *@date 2021/1/14 15:44
 */
public interface OpeWmsQualifiedPartsStockMapper extends BaseMapper {
    int updateBatch(List<OpeWmsQualifiedPartsStock> list);

    int batchInsert(@Param("list") List<OpeWmsQualifiedPartsStock> list);

    int insertOrUpdate(OpeWmsQualifiedPartsStock record);

    int insertOrUpdateSelective(OpeWmsQualifiedPartsStock record);
}