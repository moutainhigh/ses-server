package com.redescooter.ses.web.ros.service.base.impl;

import com.redescooter.ses.web.ros.dm.OpeCustomerInquiryB;
import org.springframework.stereotype.Service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.redescooter.ses.web.ros.dao.base.OpeCustomerInquiryBMapper;
import com.redescooter.ses.web.ros.service.base.OpeCustomerInquiryBService;

@Service
public class OpeCustomerInquiryBServiceImpl extends ServiceImpl<OpeCustomerInquiryBMapper, OpeCustomerInquiryB> implements OpeCustomerInquiryBService {

    @Override
    public int updateBatch(List<OpeCustomerInquiryB> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeCustomerInquiryB> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeCustomerInquiryB record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeCustomerInquiryB record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}






