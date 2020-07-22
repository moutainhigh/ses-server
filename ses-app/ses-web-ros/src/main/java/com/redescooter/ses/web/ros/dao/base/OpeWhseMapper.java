package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeWhse;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeWhseMapper extends BaseMapper<OpeWhse> {
    int batchInsert(@Param("list") List<OpeWhse> list);

    int insertOrUpdate(OpeWhse record);

    int insertOrUpdateSelective(OpeWhse record);
}
