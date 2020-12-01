package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.web.ros.dm.OpeProductPriceHistory;
import com.redescooter.ses.web.ros.dao.base.OpeProductPriceHistoryMapper;
import com.redescooter.ses.web.ros.service.base.OpeProductPriceHistoryService;

@Service
public class OpeProductPriceHistoryServiceImpl extends ServiceImpl<OpeProductPriceHistoryMapper, OpeProductPriceHistory>
    implements OpeProductPriceHistoryService {

    @Override
    public int updateBatch(List<OpeProductPriceHistory> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeProductPriceHistory> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeProductPriceHistory record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeProductPriceHistory record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
