package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dm.OpeProduct;

import java.util.List;

import com.redescooter.ses.web.ros.dao.base.OpeProductMapper;
import com.redescooter.ses.web.ros.service.base.OpeProductService;

@Service
public class OpeProductServiceImpl extends ServiceImpl<OpeProductMapper, OpeProduct> implements OpeProductService {

    @Override
    public int updateBatch(List<OpeProduct> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeProduct> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeProduct record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeProduct record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}

