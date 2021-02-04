package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeProductionPartsRelation;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author assert
 * @date 2021/2/2 14:51
 */
public interface OpeProductionPartsRelationMapper extends BaseMapper<OpeProductionPartsRelation> {
    int updateBatch(List<OpeProductionPartsRelation> list);

    int updateBatchSelective(List<OpeProductionPartsRelation> list);

    int batchInsert(@Param("list") List<OpeProductionPartsRelation> list);

    int insertOrUpdate(OpeProductionPartsRelation record);

    int insertOrUpdateSelective(OpeProductionPartsRelation record);
}