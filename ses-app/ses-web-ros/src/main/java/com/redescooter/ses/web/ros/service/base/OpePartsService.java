package com.redescooter.ses.web.ros.service.base;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeParts;

@Transactional
public interface OpePartsService extends IService<OpeParts> {


    int updateBatch(List<OpeParts> list);

    int batchInsert(List<OpeParts> list);

    int insertOrUpdate(OpeParts record);

    int insertOrUpdateSelective(OpeParts record);

}




