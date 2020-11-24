package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeEntrustPartsB;

import java.util.List;
public interface OpeEntrustPartsBService extends IService<OpeEntrustPartsB> {


    int updateBatch(List<OpeEntrustPartsB> list);

    int batchInsert(List<OpeEntrustPartsB> list);

    int insertOrUpdate(OpeEntrustPartsB record);

    int insertOrUpdateSelective(OpeEntrustPartsB record);

}
