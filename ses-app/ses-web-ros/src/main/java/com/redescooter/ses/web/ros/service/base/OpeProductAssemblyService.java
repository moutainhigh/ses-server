package com.redescooter.ses.web.ros.service.base;

import com.redescooter.ses.web.ros.dm.OpeProductAssembly;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OpeProductAssemblyService extends IService<OpeProductAssembly>{


    int updateBatch(List<OpeProductAssembly> list);

    int batchInsert(List<OpeProductAssembly> list);

    int insertOrUpdate(OpeProductAssembly record);

    int insertOrUpdateSelective(OpeProductAssembly record);

}
