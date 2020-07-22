package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeOutwhOrderB;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeOutwhOrderBMapper extends BaseMapper<OpeOutwhOrderB> {
    int updateBatch(List<OpeOutwhOrderB> list);

    int batchInsert(@Param("list") List<OpeOutwhOrderB> list);

    int insertOrUpdate(OpeOutwhOrderB record);

    int insertOrUpdateSelective(OpeOutwhOrderB record);
}
