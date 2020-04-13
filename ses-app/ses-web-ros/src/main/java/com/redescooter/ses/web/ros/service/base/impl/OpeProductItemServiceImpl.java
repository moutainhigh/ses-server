package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import com.redescooter.ses.web.ros.dm.OpeProductItem;
import com.redescooter.ses.web.ros.dao.base.OpeProductItemMapper;
import com.redescooter.ses.web.ros.service.base.OpeProductItemService;

@Service
public class OpeProductItemServiceImpl extends ServiceImpl<OpeProductItemMapper, OpeProductItem> implements OpeProductItemService {

    @Override
    public int updateBatch(List<OpeProductItem> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeProductItem> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeProductItem record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeProductItem record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}

