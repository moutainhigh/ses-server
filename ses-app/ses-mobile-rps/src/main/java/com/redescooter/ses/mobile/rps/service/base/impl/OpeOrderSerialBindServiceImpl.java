package com.redescooter.ses.mobile.rps.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.mobile.rps.dao.base.OpeOrderSerialBindMapper;
import com.redescooter.ses.mobile.rps.dm.OpeOrderSerialBind;import org.springframework.stereotype.Service;
import com.redescooter.ses.mobile.rps.service.base.OpeOrderSerialBindService;
import java.util.List;

/**
 * @author assert
 * @date 2020/12/30 15:26
 */
@Service
public class OpeOrderSerialBindServiceImpl extends ServiceImpl<OpeOrderSerialBindMapper, OpeOrderSerialBind>
        implements OpeOrderSerialBindService {


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

    @Override
    public int updateBatchSelective(List<OpeOrderSerialBind> list) {
        return baseMapper.updateBatchSelective(list);
    }
}





