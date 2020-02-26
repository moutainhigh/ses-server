package com.redescooter.ses.web.ros.service.impl.base;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.OpePartsHistoryRecordMapper;
import com.redescooter.ses.web.ros.dm.OpePartsHistoryRecord;
import com.redescooter.ses.web.ros.service.base.OpePartsHistoryRecordService;

@Service
public class OpePartsHistoryRecordServiceImpl extends ServiceImpl<OpePartsHistoryRecordMapper, OpePartsHistoryRecord> implements OpePartsHistoryRecordService {

    @Override
    public int updateBatch(List<OpePartsHistoryRecord> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpePartsHistoryRecord> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpePartsHistoryRecord record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpePartsHistoryRecord record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}

