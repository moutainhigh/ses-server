package com.redescooter.ses.web.ros.service.base;

import java.util.List;
import com.redescooter.ses.web.ros.dm.OpeInWhousePartsB;
import com.baomidou.mybatisplus.extension.service.IService;
public interface OpeInWhousePartsBService extends IService<OpeInWhousePartsB>{


    int updateBatch(List<OpeInWhousePartsB> list);

    int batchInsert(List<OpeInWhousePartsB> list);

    int insertOrUpdate(OpeInWhousePartsB record);

    int insertOrUpdateSelective(OpeInWhousePartsB record);

}
