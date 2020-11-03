package com.redescooter.ses.mobile.rps.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.mobile.rps.dm.OpeOrderQcItem;
import java.util.List;
import com.redescooter.ses.mobile.rps.dao.base.OpeOrderQcItemMapper;
import com.redescooter.ses.mobile.rps.service.base.OpeOrderQcItemService;
@Service
public class OpeOrderQcItemServiceImpl extends ServiceImpl<OpeOrderQcItemMapper, OpeOrderQcItem> implements OpeOrderQcItemService{

    @Override
    public int updateBatch(List<OpeOrderQcItem> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<OpeOrderQcItem> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(OpeOrderQcItem record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(OpeOrderQcItem record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
