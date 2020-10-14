package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.web.ros.dao.base.OpeProductionPartPriceHistoryMapper;
import com.redescooter.ses.web.ros.dm.OpeProductionPartPriceHistory;
import com.redescooter.ses.web.ros.service.base.OpeProductionPartPriceHistoryService;

@Service
public class OpeProductionPartPriceHistoryServiceImpl
    extends ServiceImpl<OpeProductionPartPriceHistoryMapper, OpeProductionPartPriceHistory>
    implements OpeProductionPartPriceHistoryService {

    @Override
    public int updateBatch(List<OpeProductionPartPriceHistory> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeProductionPartPriceHistory> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeProductionPartPriceHistory record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeProductionPartPriceHistory record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
