package com.redescooter.ses.mobile.rps.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.mobile.rps.dao.base.OpeOutWhouseOrderMapper;
import com.redescooter.ses.mobile.rps.dm.OpeOutWhouseOrder;
import org.springframework.stereotype.Service;
import com.redescooter.ses.mobile.rps.service.base.OpeOutWhouseOrderService;import java.util.List;

/**
 * @author assert
 * @date 2021/1/27 22:52
 */
@Service
public class OpeOutWhouseOrderServiceImpl extends ServiceImpl<OpeOutWhouseOrderMapper, OpeOutWhouseOrder>
        implements OpeOutWhouseOrderService {


    @Override
    public int updateBatch(List<OpeOutWhouseOrder> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int updateBatchSelective(List<OpeOutWhouseOrder> list) {
        return baseMapper.updateBatchSelective(list);
    }

    @Override
    public int batchInsert(List<OpeOutWhouseOrder> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeOutWhouseOrder record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeOutWhouseOrder record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}


