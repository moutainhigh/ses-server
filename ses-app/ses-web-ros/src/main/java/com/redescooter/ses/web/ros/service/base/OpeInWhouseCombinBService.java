package com.redescooter.ses.web.ros.service.base;

import java.util.List;
import com.redescooter.ses.web.ros.dm.OpeInWhouseCombinB;
import com.baomidou.mybatisplus.extension.service.IService;
public interface OpeInWhouseCombinBService extends IService<OpeInWhouseCombinB>{


    int updateBatch(List<OpeInWhouseCombinB> list);

    int batchInsert(List<OpeInWhouseCombinB> list);

    int insertOrUpdate(OpeInWhouseCombinB record);

    int insertOrUpdateSelective(OpeInWhouseCombinB record);

}
