package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dm.OpeAssemblyOrder;
import java.util.List;
import com.redescooter.ses.web.ros.dao.base.OpeAssemblyOrderMapper;
import com.redescooter.ses.web.ros.service.base.OpeAssemblyOrderService;

@Service
public class OpeAssemblyOrderServiceImpl extends ServiceImpl<OpeAssemblyOrderMapper, OpeAssemblyOrder> implements OpeAssemblyOrderService {

    @Override
    public int updateBatch(List<OpeAssemblyOrder> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeAssemblyOrder> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeAssemblyOrder record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeAssemblyOrder record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}









