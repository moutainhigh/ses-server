package com.redescooter.ses.wh.fr.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.wh.fr.dao.base.OpeColorMapper;
import com.redescooter.ses.wh.fr.dm.OpeColor;
import com.redescooter.ses.wh.fr.service.base.OpeColorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpeColorServiceImpl extends ServiceImpl<OpeColorMapper, OpeColor> implements OpeColorService {

    @Override
    public int updateBatch(List<OpeColor> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<OpeColor> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(OpeColor record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(OpeColor record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
