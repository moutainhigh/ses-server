package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpePartsSec;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpePartsSecMapper extends BaseMapper<OpePartsSec> {
    int updateBatch(List<OpePartsSec> list);

    int batchInsert(@Param("list") List<OpePartsSec> list);

    int insertOrUpdate(OpePartsSec record);

    int insertOrUpdateSelective(OpePartsSec record);
}