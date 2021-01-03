package com.redescooter.ses.web.website.service.impl.base;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.web.website.dao.base.SitePartsPriceMapper;
import com.redescooter.ses.web.website.dm.SitePartsPrice;
import com.redescooter.ses.web.website.service.base.SitePartsPriceService;
@Service
public class SitePartsPriceServiceImpl extends ServiceImpl<SitePartsPriceMapper, SitePartsPrice> implements SitePartsPriceService{

    @Override
    public int updateBatch(List<SitePartsPrice> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int updateBatchSelective(List<SitePartsPrice> list) {
        return baseMapper.updateBatchSelective(list);
    }
    @Override
    public int batchInsert(List<SitePartsPrice> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(SitePartsPrice record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(SitePartsPrice record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
