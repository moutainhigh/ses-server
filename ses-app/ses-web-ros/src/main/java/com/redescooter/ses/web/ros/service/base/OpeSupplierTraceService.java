package com.redescooter.ses.web.ros.service.base;

import java.util.List;
import com.redescooter.ses.web.ros.dm.OpeSupplierTrace;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OpeSupplierTraceService extends IService<OpeSupplierTrace> {


    int updateBatch(List<OpeSupplierTrace> list);

    int batchInsert(List<OpeSupplierTrace> list);

    int insertOrUpdate(OpeSupplierTrace record);

    int insertOrUpdateSelective(OpeSupplierTrace record);

}

