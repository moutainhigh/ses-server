package com.redescooter.ses.mobile.rps.service.base;

import java.util.List;
import com.redescooter.ses.mobile.rps.dm.OpeAssemblyBOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OpeAssemblyBOrderService extends IService<OpeAssemblyBOrder>{


    int updateBatch(List<OpeAssemblyBOrder> list);

    int batchInsert(List<OpeAssemblyBOrder> list);

    int insertOrUpdate(OpeAssemblyBOrder record);

    int insertOrUpdateSelective(OpeAssemblyBOrder record);

}
