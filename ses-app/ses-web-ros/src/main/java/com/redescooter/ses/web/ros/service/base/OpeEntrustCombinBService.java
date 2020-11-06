package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeEntrustCombinB;

import java.util.List;
public interface OpeEntrustCombinBService extends IService<OpeEntrustCombinB> {


    int updateBatch(List<OpeEntrustCombinB> list);

    int batchInsert(List<OpeEntrustCombinB> list);

    int insertOrUpdate(OpeEntrustCombinB record);

    int insertOrUpdateSelective(OpeEntrustCombinB record);

}
