package com.redescooter.ses.web.ros.service.base;

import java.util.List;
import com.redescooter.ses.web.ros.dm.OpeFactory;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OpeFactoryService extends IService<OpeFactory>{


    int updateBatch(List<OpeFactory> list);

    int batchInsert(List<OpeFactory> list);

    int insertOrUpdate(OpeFactory record);

    int insertOrUpdateSelective(OpeFactory record);

}
