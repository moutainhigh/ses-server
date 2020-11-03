package com.redescooter.ses.mobile.rps.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.mobile.rps.dao.base.OpeOrderSerialBindMapper;
import com.redescooter.ses.mobile.rps.dm.OpeOrderSerialBind;
import com.redescooter.ses.mobile.rps.service.base.OpeOrderSerialBindService;
@Service
public class OpeOrderSerialBindServiceImpl extends ServiceImpl<OpeOrderSerialBindMapper, OpeOrderSerialBind> implements OpeOrderSerialBindService{

    @Override
    public int updateBatch(List<OpeOrderSerialBind> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<OpeOrderSerialBind> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(OpeOrderSerialBind record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(OpeOrderSerialBind record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
