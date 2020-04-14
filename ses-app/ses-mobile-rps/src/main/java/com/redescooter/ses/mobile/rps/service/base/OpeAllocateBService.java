package com.redescooter.ses.mobile.rps.service.base;

import java.util.List;
import com.redescooter.ses.mobile.rps.dm.OpeAllocateB;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OpeAllocateBService extends IService<OpeAllocateB> {


    int updateBatch(List<OpeAllocateB> list);

    int batchInsert(List<OpeAllocateB> list);

    int insertOrUpdate(OpeAllocateB record);

    int insertOrUpdateSelective(OpeAllocateB record);

}

