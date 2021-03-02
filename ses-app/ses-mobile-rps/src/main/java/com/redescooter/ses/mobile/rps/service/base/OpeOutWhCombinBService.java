package com.redescooter.ses.mobile.rps.service.base;

import java.util.List;

import com.redescooter.ses.mobile.rps.dm.OpeOutWhCombinB;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OpeOutWhCombinBService extends IService<OpeOutWhCombinB> {


    int updateBatch(List<OpeOutWhCombinB> list);

    int batchInsert(List<OpeOutWhCombinB> list);

    int insertOrUpdate(OpeOutWhCombinB record);

    int insertOrUpdateSelective(OpeOutWhCombinB record);

    int updateBatchSelective(List<OpeOutWhCombinB> list);
}



