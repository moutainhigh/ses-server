package com.redescooter.ses.web.website.service.impl.base;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.web.website.dao.base.SitePaymentTypeMapper;
import com.redescooter.ses.web.website.dm.SitePaymentType;
import com.redescooter.ses.web.website.service.base.SitePaymentTypeService;

@Service
public class SitePaymentTypeServiceImpl extends ServiceImpl<SitePaymentTypeMapper, SitePaymentType> implements SitePaymentTypeService {

    @Override
    public int updateBatch(List<SitePaymentType> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int updateBatchSelective(List<SitePaymentType> list) {
        return baseMapper.updateBatchSelective(list);
    }

    @Override
    public int batchInsert(List<SitePaymentType> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(SitePaymentType record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(SitePaymentType record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}



