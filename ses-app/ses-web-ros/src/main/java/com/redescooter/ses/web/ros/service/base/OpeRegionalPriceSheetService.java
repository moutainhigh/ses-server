package com.redescooter.ses.web.ros.service.base;

import com.redescooter.ses.web.ros.dm.OpeRegionalPriceSheet;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OpeRegionalPriceSheetService extends IService<OpeRegionalPriceSheet> {


    int updateBatch(List<OpeRegionalPriceSheet> list);

    int batchInsert(List<OpeRegionalPriceSheet> list);

    int insertOrUpdate(OpeRegionalPriceSheet record);

    int insertOrUpdateSelective(OpeRegionalPriceSheet record);

}



