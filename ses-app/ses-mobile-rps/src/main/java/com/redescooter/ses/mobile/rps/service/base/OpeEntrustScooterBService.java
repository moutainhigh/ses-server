package com.redescooter.ses.mobile.rps.service.base;

import com.redescooter.ses.mobile.rps.dm.OpeEntrustScooterB;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OpeEntrustScooterBService extends IService<OpeEntrustScooterB> {


    int updateBatch(List<OpeEntrustScooterB> list);

    int batchInsert(List<OpeEntrustScooterB> list);

    int insertOrUpdate(OpeEntrustScooterB record);

    int insertOrUpdateSelective(OpeEntrustScooterB record);

}

