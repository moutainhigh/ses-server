package com.redescooter.ses.mobile.rps.service.base;

import java.util.List;
import com.redescooter.ses.mobile.rps.dm.OpeEntrustCombinB;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OpeEntrustCombinBService extends IService<OpeEntrustCombinB> {


    int updateBatch(List<OpeEntrustCombinB> list);

    int batchInsert(List<OpeEntrustCombinB> list);

    int insertOrUpdate(OpeEntrustCombinB record);

    int insertOrUpdateSelective(OpeEntrustCombinB record);

}

