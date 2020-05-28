package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.OpeTradePayRecordMapper;
import java.util.List;
import com.redescooter.ses.web.ros.dm.OpeTradePayRecord;
import com.redescooter.ses.web.ros.service.base.OpeTradePayRecordService;

@Service
public class OpeTradePayRecordServiceImpl extends ServiceImpl<OpeTradePayRecordMapper, OpeTradePayRecord>
    implements OpeTradePayRecordService {

    @Override
    public int updateBatch(List<OpeTradePayRecord> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeTradePayRecord> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeTradePayRecord record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeTradePayRecord record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
