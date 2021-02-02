package com.redescooter.ses.mobile.rps.service.base;

import com.redescooter.ses.mobile.rps.dm.OpeOutWhScooterB;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OpeOutWhScooterBService extends IService<OpeOutWhScooterB> {


    int updateBatch(List<OpeOutWhScooterB> list);

    int batchInsert(List<OpeOutWhScooterB> list);

    int insertOrUpdate(OpeOutWhScooterB record);

    int insertOrUpdateSelective(OpeOutWhScooterB record);

    int updateBatchSelective(List<OpeOutWhScooterB> list);
}



