package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import com.redescooter.ses.web.ros.dao.base.OpeInvoiceCombinDetailBMapper;
import com.redescooter.ses.web.ros.dm.OpeInvoiceCombinDetailB;
import com.redescooter.ses.web.ros.service.base.OpeInvoiceCombinDetailBService;

@Service
public class OpeInvoiceCombinDetailBServiceImpl extends ServiceImpl<OpeInvoiceCombinDetailBMapper, OpeInvoiceCombinDetailB> implements OpeInvoiceCombinDetailBService {

    @Override
    public int updateBatch(List<OpeInvoiceCombinDetailB> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeInvoiceCombinDetailB> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeInvoiceCombinDetailB record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeInvoiceCombinDetailB record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
