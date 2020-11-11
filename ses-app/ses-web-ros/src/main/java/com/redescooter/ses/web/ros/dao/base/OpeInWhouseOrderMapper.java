package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeInWhouseOrder;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeInWhouseOrderMapper extends BaseMapper<OpeInWhouseOrder> {
    int updateBatch(List<OpeInWhouseOrder> list);

    int batchInsert(@Param("list") List<OpeInWhouseOrder> list);

    int insertOrUpdate(OpeInWhouseOrder record);

    int insertOrUpdateSelective(OpeInWhouseOrder record);
}