package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeInWhouseScooterB;import org.apache.ibatis.annotations.Param;import java.util.List;

public interface OpeInWhouseScooterBMapper extends BaseMapper<OpeInWhouseScooterB> {
    int updateBatch(List<OpeInWhouseScooterB> list);

    int batchInsert(@Param("list") List<OpeInWhouseScooterB> list);

    int insertOrUpdate(OpeInWhouseScooterB record);

    int insertOrUpdateSelective(OpeInWhouseScooterB record);
}
