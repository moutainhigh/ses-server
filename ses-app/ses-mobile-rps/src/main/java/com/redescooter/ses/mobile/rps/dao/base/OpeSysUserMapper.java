package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeSysUser;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeSysUserMapper extends BaseMapper<OpeSysUser> {
    int updateBatch(List<OpeSysUser> list);

    int batchInsert(@Param("list") List<OpeSysUser> list);

    int insertOrUpdate(OpeSysUser record);

    int insertOrUpdateSelective(OpeSysUser record);
}