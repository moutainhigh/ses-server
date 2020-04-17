package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeSysUser;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeSysUserMapper extends BaseMapper<OpeSysUser> {
    int updateBatch(List<OpeSysUser> list);

    int batchInsert(@Param("list") List<OpeSysUser> list);

    int insertOrUpdate(OpeSysUser record);

    int insertOrUpdateSelective(OpeSysUser record);
}