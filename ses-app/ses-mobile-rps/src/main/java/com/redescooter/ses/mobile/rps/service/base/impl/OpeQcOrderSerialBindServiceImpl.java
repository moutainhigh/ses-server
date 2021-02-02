package com.redescooter.ses.mobile.rps.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.mobile.rps.dao.base.OpeQcOrderSerialBindMapper;
import com.redescooter.ses.mobile.rps.dm.OpeQcOrderSerialBind;
import org.springframework.stereotype.Service;
import com.redescooter.ses.mobile.rps.service.base.OpeQcOrderSerialBindService;
import java.util.List;

/**
 * @author assert
 * @date 2021/1/25 10:02
 */
@Service
public class OpeQcOrderSerialBindServiceImpl extends ServiceImpl<OpeQcOrderSerialBindMapper, OpeQcOrderSerialBind>
        implements OpeQcOrderSerialBindService {

    @Override
    public int updateBatch(List<OpeQcOrderSerialBind> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int updateBatchSelective(List<OpeQcOrderSerialBind> list) {
        return baseMapper.updateBatchSelective(list);
    }

    @Override
    public int batchInsert(List<OpeQcOrderSerialBind> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeQcOrderSerialBind record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeQcOrderSerialBind record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}




