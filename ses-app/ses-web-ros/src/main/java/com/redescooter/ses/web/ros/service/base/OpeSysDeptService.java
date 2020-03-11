package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeSysDept;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface OpeSysDeptService extends IService<OpeSysDept> {


    int updateBatch(List<OpeSysDept> list);

    int batchInsert(List<OpeSysDept> list);

    int insertOrUpdate(OpeSysDept record);

    int insertOrUpdateSelective(OpeSysDept record);

}
