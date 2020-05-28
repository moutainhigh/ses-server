package com.redescooter.ses.mobile.rps.service.base;

import com.redescooter.ses.mobile.rps.dm.OpeAssemblyLotTrace;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OpeAssemblyLotTraceService extends IService<OpeAssemblyLotTrace> {


    int updateBatch(List<OpeAssemblyLotTrace> list);

    int updateBatchSelective(List<OpeAssemblyLotTrace> list);

    int batchInsert(List<OpeAssemblyLotTrace> list);

    int insertOrUpdate(OpeAssemblyLotTrace record);

    int insertOrUpdateSelective(OpeAssemblyLotTrace record);

}

