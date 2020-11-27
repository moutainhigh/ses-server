package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeSysStaff;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OpeSysStaffMapper extends BaseMapper<OpeSysStaff> {
    int updateBatch(List<OpeSysStaff> list);

    int batchInsert(@Param("list") List<OpeSysStaff> list);

    int insertOrUpdate(OpeSysStaff record);

    int insertOrUpdateSelective(OpeSysStaff record);
}