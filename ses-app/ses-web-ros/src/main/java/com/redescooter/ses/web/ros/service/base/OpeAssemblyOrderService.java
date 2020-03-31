package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeAssemblyOrder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface OpeAssemblyOrderService extends IService<OpeAssemblyOrder> {


    int batchInsert(List<OpeAssemblyOrder> list);

    int insertOrUpdate(OpeAssemblyOrder record);

    int insertOrUpdateSelective(OpeAssemblyOrder record);

    int updateBatch(List<OpeAssemblyOrder> list);
}


