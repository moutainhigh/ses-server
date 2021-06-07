package com.redescooter.ses.wh.fr.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.wh.fr.dao.base.OpeCustomerInquiryBMapper;
import com.redescooter.ses.wh.fr.dm.OpeCustomerInquiryB;
import com.redescooter.ses.wh.fr.service.base.OpeCustomerInquiryBService;
import org.springframework.stereotype.Service;

import java.util.List;

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






