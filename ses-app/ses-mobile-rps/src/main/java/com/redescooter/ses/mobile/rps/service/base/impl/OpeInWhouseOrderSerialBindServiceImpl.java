package com.redescooter.ses.mobile.rps.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.mobile.rps.dao.base.OpeInWhouseOrderSerialBindMapper;
import com.redescooter.ses.mobile.rps.dm.OpeInWhouseOrderSerialBind;import org.springframework.stereotype.Service;
import com.redescooter.ses.mobile.rps.service.base.OpeInWhouseOrderSerialBindService;import java.util.List;

/**
 * @author assert
 * @date 2021/1/22 17:47
 */
@Service
public class OpeInWhouseOrderSerialBindServiceImpl extends ServiceImpl<OpeInWhouseOrderSerialBindMapper, OpeInWhouseOrderSerialBind>
        implements OpeInWhouseOrderSerialBindService {

    @Override
    public int updateBatch(List<OpeInWhouseOrderSerialBind> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int updateBatchSelective(List<OpeInWhouseOrderSerialBind> list) {
        return baseMapper.updateBatchSelective(list);
    }

    @Override
    public int batchInsert(List<OpeInWhouseOrderSerialBind> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeInWhouseOrderSerialBind record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeInWhouseOrderSerialBind record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}




