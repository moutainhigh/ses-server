package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeProductionParts;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author assert
 * @date 2021/1/29 11:29
 */
public interface OpeProductionPartsMapper extends BaseMapper<OpeProductionParts> {
    int updateBatch(List<OpeProductionParts> list);

    int updateBatchSelective(List<OpeProductionParts> list);

    int batchInsert(@Param("list") List<OpeProductionParts> list);

    int insertOrUpdate(OpeProductionParts record);

    int insertOrUpdateSelective(OpeProductionParts record);
}