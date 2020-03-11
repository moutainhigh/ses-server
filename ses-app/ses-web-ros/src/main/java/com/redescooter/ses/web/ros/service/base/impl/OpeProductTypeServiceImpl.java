package com.redescooter.ses.web.ros.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.OpeProductTypeMapper;
import com.redescooter.ses.web.ros.dm.OpeProductType;
import com.redescooter.ses.web.ros.service.base.OpeProductTypeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpeProductTypeServiceImpl extends ServiceImpl<OpeProductTypeMapper, OpeProductType> implements OpeProductTypeService {

    @Override
    public int updateBatch(List<OpeProductType> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeProductType> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeProductType record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeProductType record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
