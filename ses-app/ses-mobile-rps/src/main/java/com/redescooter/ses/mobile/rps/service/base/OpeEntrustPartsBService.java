package com.redescooter.ses.mobile.rps.service.base;

import com.redescooter.ses.mobile.rps.dm.OpeEntrustPartsB;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OpeEntrustPartsBService extends IService<OpeEntrustPartsB> {


    int updateBatch(List<OpeEntrustPartsB> list);

    int batchInsert(List<OpeEntrustPartsB> list);

    int insertOrUpdate(OpeEntrustPartsB record);

    int insertOrUpdateSelective(OpeEntrustPartsB record);

}

