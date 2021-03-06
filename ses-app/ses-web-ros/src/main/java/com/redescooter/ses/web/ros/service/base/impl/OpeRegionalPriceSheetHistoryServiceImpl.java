package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.OpeRegionalPriceSheetHistoryMapper;
import java.util.List;
import com.redescooter.ses.web.ros.dm.OpeRegionalPriceSheetHistory;
import com.redescooter.ses.web.ros.service.base.OpeRegionalPriceSheetHistoryService;

@Service
public class OpeRegionalPriceSheetHistoryServiceImpl extends ServiceImpl<OpeRegionalPriceSheetHistoryMapper, OpeRegionalPriceSheetHistory> implements OpeRegionalPriceSheetHistoryService {

    @Override
    public int updateBatch(List<OpeRegionalPriceSheetHistory> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeRegionalPriceSheetHistory> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeRegionalPriceSheetHistory record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeRegionalPriceSheetHistory record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}




