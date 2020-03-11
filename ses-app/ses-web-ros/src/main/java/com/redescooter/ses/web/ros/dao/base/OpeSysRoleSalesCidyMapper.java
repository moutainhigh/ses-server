package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeSysRoleSalesCidy;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OpeSysRoleSalesCidyMapper extends BaseMapper<OpeSysRoleSalesCidy> {
    int batchInsert(@Param("list") List<OpeSysRoleSalesCidy> list);

    int insertOrUpdate(OpeSysRoleSalesCidy record);

    int insertOrUpdateSelective(OpeSysRoleSalesCidy record);
}