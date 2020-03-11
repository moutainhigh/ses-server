package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeSysRoleSalesCidy;

import java.util.List;

public interface OpeSysRoleSalesCidyService extends IService<OpeSysRoleSalesCidy> {


    int batchInsert(List<OpeSysRoleSalesCidy> list);

    int insertOrUpdate(OpeSysRoleSalesCidy record);

    int insertOrUpdateSelective(OpeSysRoleSalesCidy record);

}
