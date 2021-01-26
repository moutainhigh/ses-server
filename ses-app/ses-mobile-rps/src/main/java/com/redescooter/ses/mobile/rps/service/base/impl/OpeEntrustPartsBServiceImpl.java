package com.redescooter.ses.mobile.rps.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.mobile.rps.dm.OpeEntrustPartsB;
import java.util.List;
import com.redescooter.ses.mobile.rps.dao.base.OpeEntrustPartsBMapper;
import com.redescooter.ses.mobile.rps.service.base.OpeEntrustPartsBService;

@Service
public class OpeEntrustPartsBServiceImpl extends ServiceImpl<OpeEntrustPartsBMapper, OpeEntrustPartsB> implements OpeEntrustPartsBService {

    @Override
    public int updateBatch(List<OpeEntrustPartsB> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeEntrustPartsB> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeEntrustPartsB record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeEntrustPartsB record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}

