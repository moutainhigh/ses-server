package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.web.ros.dm.OpeInWhouseOrder;
import com.redescooter.ses.web.ros.dao.base.OpeInWhouseOrderMapper;
import com.redescooter.ses.web.ros.service.base.OpeInWhouseOrderService;

@Service
public class OpeInWhouseOrderServiceImpl extends ServiceImpl<OpeInWhouseOrderMapper, OpeInWhouseOrder> implements OpeInWhouseOrderService {

    @Override
    public int updateBatch(List<OpeInWhouseOrder> list) {
        return baseMapper.updateBatch(list);
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

