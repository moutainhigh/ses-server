package com.redescooter.ses.web.ros.service.impl.base;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.OpePriceSheetHistoryMapper;
import com.redescooter.ses.web.ros.dm.OpePriceSheetHistory;
import com.redescooter.ses.web.ros.service.base.OpePriceSheetHistoryService;
@Service
public class OpePriceSheetHistoryServiceImpl extends ServiceImpl<OpePriceSheetHistoryMapper, OpePriceSheetHistory> implements OpePriceSheetHistoryService{

    @Override
    public int updateBatch(List<OpePriceSheetHistory> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<OpePriceSheetHistory> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(OpePriceSheetHistory record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(OpePriceSheetHistory record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
