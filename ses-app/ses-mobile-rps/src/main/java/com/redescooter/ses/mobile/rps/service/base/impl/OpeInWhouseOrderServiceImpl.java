package com.redescooter.ses.mobile.rps.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.mobile.rps.dao.base.OpeInWhouseOrderMapper;
import com.redescooter.ses.mobile.rps.dm.OpeInWhouseOrder;
import org.springframework.stereotype.Service;
import com.redescooter.ses.mobile.rps.service.base.OpeInWhouseOrderService;
import java.util.List;

/**
 * @author assert
 * @date 2021/1/18 10:46
 */
@Service
public class OpeInWhouseOrderServiceImpl extends ServiceImpl<OpeInWhouseOrderMapper, OpeInWhouseOrder>
        implements OpeInWhouseOrderService {

    @Override
    public int updateBatch(List<OpeInWhouseOrder> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int updateBatchSelective(List<OpeInWhouseOrder> list) {
        return baseMapper.updateBatchSelective(list);
    }

    @Override
    public int batchInsert(List<OpeInWhouseOrder> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeInWhouseOrder record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeInWhouseOrder record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}



