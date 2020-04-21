package com.redescooter.ses.mobile.rps.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.mobile.rps.dao.base.OpeAssemblyQcItemMapper;
import com.redescooter.ses.mobile.rps.dm.OpeAssemblyQcItem;
import com.redescooter.ses.mobile.rps.service.base.OpeAssemblyQcItemService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpeAssemblyQcItemServiceImpl extends ServiceImpl<OpeAssemblyQcItemMapper, OpeAssemblyQcItem> implements OpeAssemblyQcItemService {

    @Override
    public int updateBatch(List<OpeAssemblyQcItem> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeAssemblyQcItem> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeAssemblyQcItem record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeAssemblyQcItem record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}

