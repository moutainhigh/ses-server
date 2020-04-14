package com.redescooter.ses.mobile.rps.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.mobile.rps.dm.OpePartsProductB;
import com.redescooter.ses.mobile.rps.dao.base.OpePartsProductBMapper;
import com.redescooter.ses.mobile.rps.service.base.OpePartsProductBService;

@Service
public class OpePartsProductBServiceImpl extends ServiceImpl<OpePartsProductBMapper, OpePartsProductB> implements OpePartsProductBService {

    @Override
    public int updateBatch(List<OpePartsProductB> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpePartsProductB> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpePartsProductB record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpePartsProductB record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}

