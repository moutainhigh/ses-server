package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeSysMenu;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface OpeSysMenuService extends IService<OpeSysMenu> {


    int updateBatch(List<OpeSysMenu> list);

    int batchInsert(List<OpeSysMenu> list);

    int insertOrUpdate(OpeSysMenu record);

    int insertOrUpdateSelective(OpeSysMenu record);

}

