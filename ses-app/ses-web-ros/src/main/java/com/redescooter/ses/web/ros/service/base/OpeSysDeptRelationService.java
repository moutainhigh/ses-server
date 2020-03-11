package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeSysDeptRelation;

import java.util.List;

public interface OpeSysDeptRelationService extends IService<OpeSysDeptRelation> {


    int batchInsert(List<OpeSysDeptRelation> list);

    int insertOrUpdate(OpeSysDeptRelation record);

    int insertOrUpdateSelective(OpeSysDeptRelation record);

}
