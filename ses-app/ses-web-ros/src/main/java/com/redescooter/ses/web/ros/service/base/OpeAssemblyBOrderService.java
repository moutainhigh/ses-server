package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeAssemblyBOrder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface OpeAssemblyBOrderService extends IService<OpeAssemblyBOrder> {


    int batchInsert(List<OpeAssemblyBOrder> list);

    int insertOrUpdate(OpeAssemblyBOrder record);

    int insertOrUpdateSelective(OpeAssemblyBOrder record);

    int updateBatch(List<OpeAssemblyBOrder> list);
}








