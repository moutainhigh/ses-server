package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeContactUs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeContactUsMapper extends BaseMapper<OpeContactUs> {
    int updateBatch(List<OpeContactUs> list);

    int batchInsert(@Param("list") List<OpeContactUs> list);

    int insertOrUpdate(OpeContactUs record);

    int insertOrUpdateSelective(OpeContactUs record);

    int updateBatchSelective(List<OpeContactUs> list);
}