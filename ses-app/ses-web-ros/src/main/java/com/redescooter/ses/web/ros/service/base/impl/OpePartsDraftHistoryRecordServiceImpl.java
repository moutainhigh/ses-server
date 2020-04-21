package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.OpePartsDraftHistoryRecordMapper;
import java.util.List;
import com.redescooter.ses.web.ros.dm.OpePartsDraftHistoryRecord;
import com.redescooter.ses.web.ros.service.base.OpePartsDraftHistoryRecordService;
@Service
public class OpePartsDraftHistoryRecordServiceImpl extends ServiceImpl<OpePartsDraftHistoryRecordMapper, OpePartsDraftHistoryRecord> implements OpePartsDraftHistoryRecordService{

    @Override
    public int updateBatch(List<OpePartsDraftHistoryRecord> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<OpePartsDraftHistoryRecord> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(OpePartsDraftHistoryRecord record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(OpePartsDraftHistoryRecord record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
