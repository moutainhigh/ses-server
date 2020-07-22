package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeOutwhOrder;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeOutwhOrderMapper extends BaseMapper<OpeOutwhOrder> {
    int updateBatch(List<OpeOutwhOrder> list);

    int batchInsert(@Param("list") List<OpeOutwhOrder> list);

    int insertOrUpdate(OpeOutwhOrder record);

    int insertOrUpdateSelective(OpeOutwhOrder record);
}
