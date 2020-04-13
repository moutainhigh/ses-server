package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import com.redescooter.ses.web.ros.dao.base.OpeProductionProductMapper;
import com.redescooter.ses.web.ros.dm.OpeProductionProduct;
import com.redescooter.ses.web.ros.service.base.OpeProductionProductService;

@Service
public class OpeProductionProductServiceImpl extends ServiceImpl<OpeProductionProductMapper, OpeProductionProduct> implements OpeProductionProductService {

    @Override
    public int updateBatch(List<OpeProductionProduct> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeProductionProduct> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeProductionProduct record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeProductionProduct record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
