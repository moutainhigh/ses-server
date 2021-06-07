package com.redescooter.ses.mobile.wh.fr.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.mobile.wh.fr.dao.base.OpeCustomerInquiryMapper;
import com.redescooter.ses.mobile.wh.fr.dm.OpeCustomerInquiry;
import com.redescooter.ses.mobile.wh.fr.service.base.OpeCustomerInquiryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpeCustomerInquiryServiceImpl extends ServiceImpl<OpeCustomerInquiryMapper, OpeCustomerInquiry> implements OpeCustomerInquiryService {

    @Override
    public int updateBatch(List<OpeCustomerInquiry> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeCustomerInquiry> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeCustomerInquiry record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeCustomerInquiry record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
















