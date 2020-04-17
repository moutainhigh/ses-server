package com.redescooter.ses.web.ros.service.base;

import java.util.List;
import com.redescooter.ses.web.ros.dm.OpeSysDeptRelation;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OpeSysDeptRelationService extends IService<OpeSysDeptRelation> {


    int batchInsert(List<OpeSysDeptRelation> list);

    int insertOrUpdate(OpeSysDeptRelation record);

    int insertOrUpdateSelective(OpeSysDeptRelation record);

}

