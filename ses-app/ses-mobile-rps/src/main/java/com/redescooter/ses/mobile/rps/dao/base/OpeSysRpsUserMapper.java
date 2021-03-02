package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeSysRpsUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OpeSysRpsUserMapper extends BaseMapper<OpeSysRpsUser> {
    int updateBatch(List<OpeSysRpsUser> list);

    int batchInsert(@Param("list") List<OpeSysRpsUser> list);

    int insertOrUpdate(OpeSysRpsUser record);

    int insertOrUpdateSelective(OpeSysRpsUser record);
}