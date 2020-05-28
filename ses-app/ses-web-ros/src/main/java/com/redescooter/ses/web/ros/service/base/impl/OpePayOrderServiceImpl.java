package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dm.OpePayOrder;
import java.util.List;
import com.redescooter.ses.web.ros.dao.base.OpePayOrderMapper;
import com.redescooter.ses.web.ros.service.base.OpePayOrderService;

@Service
public class OpePayOrderServiceImpl extends ServiceImpl<OpePayOrderMapper, OpePayOrder> implements OpePayOrderService {

    @Override
    public int updateBatch(List<OpePayOrder> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpePayOrder> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpePayOrder record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpePayOrder record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
