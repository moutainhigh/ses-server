package com.redescooter.ses.web.website.service.impl.base;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.web.website.dao.base.SiteOrderMapper;
import com.redescooter.ses.web.website.dm.SiteOrder;
import com.redescooter.ses.web.website.service.base.SiteOrderService;
@Service
public class SiteOrderServiceImpl extends ServiceImpl<SiteOrderMapper, SiteOrder> implements SiteOrderService{

    @Override
    public int updateBatch(List<SiteOrder> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int updateBatchSelective(List<SiteOrder> list) {
        return baseMapper.updateBatchSelective(list);
    }
    @Override
    public int batchInsert(List<SiteOrder> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(SiteOrder record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(SiteOrder record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
