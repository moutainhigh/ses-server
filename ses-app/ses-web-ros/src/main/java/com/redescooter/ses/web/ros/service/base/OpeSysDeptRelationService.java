package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeSysDeptRelation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface OpeSysDeptRelationService extends IService<OpeSysDeptRelation> {


    int updateBatch(List<OpeSysDeptRelation> list);

    int batchInsert(List<OpeSysDeptRelation> list);

    int insertOrUpdate(OpeSysDeptRelation record);

    int insertOrUpdateSelective(OpeSysDeptRelation record);

}



