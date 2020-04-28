package com.redescooter.ses.mobile.rps.service.base;

import java.util.List;
import com.redescooter.ses.mobile.rps.dm.OpeAssemblyOrderPart;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OpeAssemblyOrderPartService extends IService<OpeAssemblyOrderPart>{


    int updateBatch(List<OpeAssemblyOrderPart> list);

    int batchInsert(List<OpeAssemblyOrderPart> list);

    int insertOrUpdate(OpeAssemblyOrderPart record);

    int insertOrUpdateSelective(OpeAssemblyOrderPart record);

}
