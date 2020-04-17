package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeSysRoleSalesCidy;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeSysRoleSalesCidyMapper extends BaseMapper<OpeSysRoleSalesCidy> {
    int batchInsert(@Param("list") List<OpeSysRoleSalesCidy> list);

    int insertOrUpdate(OpeSysRoleSalesCidy record);

    int insertOrUpdateSelective(OpeSysRoleSalesCidy record);
}