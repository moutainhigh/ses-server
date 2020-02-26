package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpePartsType;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpePartsTypeMapper extends BaseMapper<OpePartsType> {
    int updateBatch(List<OpePartsType> list);

    int batchInsert(@Param("list") List<OpePartsType> list);

    int insertOrUpdate(OpePartsType record);

    int insertOrUpdateSelective(OpePartsType record);
}