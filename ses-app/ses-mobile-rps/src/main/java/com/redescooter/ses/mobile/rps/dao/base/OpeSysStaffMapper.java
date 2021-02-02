package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeSysStaff;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeSysStaffMapper extends BaseMapper<OpeSysStaff> {
    int updateBatch(List<OpeSysStaff> list);

    int batchInsert(@Param("list") List<OpeSysStaff> list);

    int insertOrUpdate(OpeSysStaff record);

    int insertOrUpdateSelective(OpeSysStaff record);
}