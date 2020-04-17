package com.redescooter.ses.web.ros.service.base;

import java.util.List;
import com.redescooter.ses.web.ros.dm.OpeSysRoleSalesCidy;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OpeSysRoleSalesCidyService extends IService<OpeSysRoleSalesCidy>{


    int batchInsert(List<OpeSysRoleSalesCidy> list);

    int insertOrUpdate(OpeSysRoleSalesCidy record);

    int insertOrUpdateSelective(OpeSysRoleSalesCidy record);

}
