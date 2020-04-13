package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeAllocateB;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface OpeAllocateBService extends IService<OpeAllocateB> {


    int updateBatch(List<OpeAllocateB> list);

    int batchInsert(List<OpeAllocateB> list);

    int insertOrUpdate(OpeAllocateB record);

    int insertOrUpdateSelective(OpeAllocateB record);

}

