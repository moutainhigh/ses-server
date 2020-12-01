package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeEntrustScooterB;

import java.util.List;
public interface OpeEntrustScooterBService extends IService<OpeEntrustScooterB> {


    int updateBatch(List<OpeEntrustScooterB> list);

    int batchInsert(List<OpeEntrustScooterB> list);

    int insertOrUpdate(OpeEntrustScooterB record);

    int insertOrUpdateSelective(OpeEntrustScooterB record);

}
