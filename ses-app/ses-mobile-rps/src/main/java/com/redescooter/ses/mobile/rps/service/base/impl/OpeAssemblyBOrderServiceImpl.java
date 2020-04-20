package com.redescooter.ses.mobile.rps.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.mobile.rps.dao.base.OpeAssemblyBOrderMapper;
import com.redescooter.ses.mobile.rps.dm.OpeAssemblyBOrder;
import com.redescooter.ses.mobile.rps.service.base.OpeAssemblyBOrderService;

@Service
public class OpeAssemblyBOrderServiceImpl extends ServiceImpl<OpeAssemblyBOrderMapper, OpeAssemblyBOrder> implements OpeAssemblyBOrderService {

    @Override
    public int updateBatch(List<OpeAssemblyBOrder> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeAssemblyBOrder> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeAssemblyBOrder record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeAssemblyBOrder record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}











