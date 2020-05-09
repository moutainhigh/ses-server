package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeSupplierTrace;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface OpeSupplierTraceService extends IService<OpeSupplierTrace> {


    int updateBatch(List<OpeSupplierTrace> list);

    int batchInsert(List<OpeSupplierTrace> list);

    int insertOrUpdate(OpeSupplierTrace record);

    int insertOrUpdateSelective(OpeSupplierTrace record);

}


