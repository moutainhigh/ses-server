package com.redescooter.ses.web.ros.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.OpeSupplierMapper;
import com.redescooter.ses.web.ros.dm.OpeSupplier;
import com.redescooter.ses.web.ros.service.base.OpeSupplierService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpeSupplierServiceImpl extends ServiceImpl<OpeSupplierMapper, OpeSupplier> implements OpeSupplierService {

    @Override
    public int updateBatch(List<OpeSupplier> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<OpeSupplier> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(OpeSupplier record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(OpeSupplier record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
