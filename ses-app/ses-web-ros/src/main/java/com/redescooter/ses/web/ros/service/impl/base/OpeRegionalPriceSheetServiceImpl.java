package com.redescooter.ses.web.ros.service.impl.base;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.OpeRegionalPriceSheetMapper;
import com.redescooter.ses.web.ros.dm.OpeRegionalPriceSheet;
import com.redescooter.ses.web.ros.service.base.OpeRegionalPriceSheetService;
@Service
public class OpeRegionalPriceSheetServiceImpl extends ServiceImpl<OpeRegionalPriceSheetMapper, OpeRegionalPriceSheet> implements OpeRegionalPriceSheetService{

    @Override
    public int updateBatch(List<OpeRegionalPriceSheet> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<OpeRegionalPriceSheet> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(OpeRegionalPriceSheet record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(OpeRegionalPriceSheet record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
