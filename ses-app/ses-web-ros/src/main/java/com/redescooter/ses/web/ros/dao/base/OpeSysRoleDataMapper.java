package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeSysRoleData;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeSysRoleDataMapper extends BaseMapper<OpeSysRoleData> {
    int updateBatch(List<OpeSysRoleData> list);

    int updateBatchSelective(List<OpeSysRoleData> list);

    int batchInsert(@Param("list") List<OpeSysRoleData> list);

    int insertOrUpdate(OpeSysRoleData record);

    int insertOrUpdateSelective(OpeSysRoleData record);
}