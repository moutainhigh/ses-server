package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeProductionPartsRelation;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeProductionPartsRelationMapper extends BaseMapper<OpeProductionPartsRelation> {
    int updateBatch(List<OpeProductionPartsRelation> list);

    int batchInsert(@Param("list") List<OpeProductionPartsRelation> list);

    int insertOrUpdate(OpeProductionPartsRelation record);

    int insertOrUpdateSelective(OpeProductionPartsRelation record);
}