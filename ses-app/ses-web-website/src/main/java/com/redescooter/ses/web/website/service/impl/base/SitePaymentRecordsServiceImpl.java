package com.redescooter.ses.web.website.service.impl.base;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.web.website.dm.SitePaymentRecords;
import com.redescooter.ses.web.website.dao.base.SitePaymentRecordsMapper;
import com.redescooter.ses.web.website.service.base.SitePaymentRecordsService;
@Service
public class SitePaymentRecordsServiceImpl extends ServiceImpl<SitePaymentRecordsMapper, SitePaymentRecords> implements SitePaymentRecordsService{

    @Override
    public int updateBatch(List<SitePaymentRecords> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int updateBatchSelective(List<SitePaymentRecords> list) {
        return baseMapper.updateBatchSelective(list);
    }
    @Override
    public int batchInsert(List<SitePaymentRecords> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(SitePaymentRecords record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(SitePaymentRecords record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
