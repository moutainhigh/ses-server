package com.redescooter.ses.mobile.rps.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.mobile.rps.dao.base.OpeQcOrderMapper;
import com.redescooter.ses.mobile.rps.dm.OpeQcOrder;
import org.springframework.stereotype.Service;
import com.redescooter.ses.mobile.rps.service.base.OpeQcOrderService;
import java.util.List;

/**
 * @author assert
 * @date 2021/1/26 10:56
 */
@Service
public class OpeQcOrderServiceImpl extends ServiceImpl<OpeQcOrderMapper, OpeQcOrder>
        implements OpeQcOrderService {

    @Override
    public int updateBatch(List<OpeQcOrder> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int updateBatchSelective(List<OpeQcOrder> list) {
        return baseMapper.updateBatchSelective(list);
    }

    @Override
    public int batchInsert(List<OpeQcOrder> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeQcOrder record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeQcOrder record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}


